package FPN.src;

public class ALU {

    //实现浮点数的加法
    //@param num1 被加数，String类型
    //@param num2 加数，String类型
    //@return String
    public String Addition(String num1 , String num2){
        float_point_number N1 = new float_point_number(num1);
        float_point_number N2 = new float_point_number(num2);

        int[] number1 = N1.getNumber();
        int signBit1 = N1.getSignBit();
        int[] Bias_exponent1 = N1.getBias_exponent();
        int[] Fraction1 = N1.getFraction();

        int[] number2 = N2.getNumber();
        int signBit2 = N2.getSignBit();
        int[] Bias_exponent2 = N2.getBias_exponent();
        int[] Fraction2 = N2.getFraction();

        if (N1.CheckZero(number1)){
            return num2;
        }
        if (N2.CheckZero(number2)){
            return num1;
        }

        /*
        如何判定结果的符号？
        如果同号相加，那么结果的符号一定和原来的符号相同
        如果异号相加
        */



        //右移的时候要注意幅值的开头默认值是1，所以在这里把23位（52位）的幅值拓展一位，便于下面操作;
        // 拓展两位，好确定符号
        int[]x = {1};
        int[] tempFraction1 = N1.Combine(x,N1.getFraction());
        int[] tempFraction2 = N2.Combine(x,N2.getFraction());


        //如果n1的指数部分比n2大，那么就要右移n2的有效值，然后n2的阶值加一；还要通过Bias_exponent_Check_Zero函数判断一下指数是否为0
        int[] BINONE = N2.NEWINT(N2.getBias_exponent().length,0);
        BINONE[N2.getBias_exponent().length - 1] = 1;

        if (N1.Bias_exponent_Comparing(N1.getBias_exponent(),N2.getBias_exponent())){
            while(! ArraysJudge(N1.getBias_exponent(),N2.getBias_exponent())){//两个指数不相等的时候
                //有效值右移
                tempFraction2 = N2.RIGHTMOVE(tempFraction2);
                //阶值加一
                N2.Bias_exponent = N2.binaryAddition(N2.getBias_exponent(),BINONE);
                N2.JingWei = false;

                //如果阶值为0
                if (N2.Bias_exponent_Check_Zero(N2.getBias_exponent())){
                    return num1;
                }
            }
        }
        //如果n1的指数部分比n2小，那么就要右移n1的有效值，然后n1的阶值加一；还要通过Bias_exponent_Check_Zero函数判断一下指数是否为0
        else if (! N1.Bias_exponent_Comparing(N1.getBias_exponent(),N2.getBias_exponent())){
            while(! ArraysJudge(N1.getBias_exponent(),N2.getBias_exponent())){//两个指数不相等的时候
                tempFraction1 = N1.RIGHTMOVE(tempFraction1);
                N1.Bias_exponent = N1.binaryAddition(N1.getBias_exponent(),BINONE);
                N1.JingWei = false;
                //如果阶值位0
                if (N1.Bias_exponent_Check_Zero(N1.getBias_exponent())){
                    return num2;
                }
            }
        }

        //这时候阶值相等了，直接进行浮点数加法运算
        // 有效值相加:
        // 1.根据浮点数本身的符号，把有效值转变为带符号有效值（如果前面的符号位负就取补码）
        // 2.将带符号的有效值相加
        // 3.判断结果是否为0
        // 4.如果结果不为0，判断结果的符号；如果结果的符号位正数，那么就处理掉前面三位
        // ，如果结果的符号位负数，那就取补码得到真实的偏移量，然后将结果的符号设为负号并进行舍去处理
        // 5.溢出处理


        //如果符号一样，那么可能会溢出，而且结果的符号和这两个操作数的符号一定是一样的。要处理有效值溢出的情况
        if (N1.getSignBit() == N2.getSignBit()){
            int[] resultFraction = N1.binaryAddition(tempFraction1,tempFraction2);
            //最高位产生了进位，要进行有效值右移、阶值加一；在右移的时候要检测是否上阶值上溢
            if (N1.JingWei == true){
                //先还原JingWei
                N1.JingWei = false;
                N1.RIGHTMOVE(resultFraction);
                N1.Bias_exponent = N1.binaryAddition(N1.getBias_exponent(),BINONE);
                //操作完毕，检测是否上溢
                if (BiasOverflow(N1)){
                    StringBuilder sb = new StringBuilder();
                    sb.append(N1.getSignBit());
                    for (int i = 0; i < N1.getBias_exponent().length ; i++){
                        sb.append(1);
                    }
                    for (int i = 0 ; i < N1.getFraction().length ; i ++){
                        sb.append(0);
                    }
                    return sb.toString();
                }
                /*
                //如果没有上溢
                else {
                    N1.Fraction = N1.Intercept_array(resultFraction,1,resultFraction.length - 1);
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append(N1.getSignBit());
                    stringBuilder1.append(TurnArraysToString(N1.getBias_exponent()));
                    stringBuilder1.append(TurnArraysToString(N1.getFraction()));
                    return stringBuilder1.toString();
                }
                */
            }
            //没有产生进位。将resultFraction的1，23位导入进N1中
            else {
                N1.Fraction = N1.Intercept_array(resultFraction,1,resultFraction.length - 1);
                if (resultFraction[0] == 1){
                    N1.GuiGeHua = true;
                }
                else N1.GuiGeHua = false;

            }
        }
        //符号不同的情况。一定不会溢出。但是要考虑结果的符号问题
        else {
            //先比较两个操作数的绝对值的大小,借用Bias_exponent_Comparing函数
            if (! N1.Bias_exponent_Comparing(tempFraction1,tempFraction2)){
                //第一个操作数的绝对值比第二个操作数绝对值大，那么最终结果的符号应该和第一个操作符的符号是一样的
                //如果比第二个小那么最终结果的符号和第二个操作数一样
                N1.signBit = N2.getSignBit();
            }


            if (N1.Bias_exponent_Comparing(tempFraction1,tempFraction2)){
                int[] a = {0};
                int[] resultFraction1 = N1.binarySub(N1.Combine(a,tempFraction1),N1.Combine(a,tempFraction2));
                N1.Fraction = N1.Intercept_array(resultFraction1,2,resultFraction1.length);
                if (resultFraction1[1] == 1){
                    N1.GuiGeHua = true;
                }
                else N1.GuiGeHua = false;
            }
            else {
                int[] a = {0};
                int[] resultFraction1 = N1.binarySub(N1.Combine(a,tempFraction2),N1.Combine(a,tempFraction1));
                N1.Fraction = N1.Intercept_array(resultFraction1,2,resultFraction1.length);
                if (resultFraction1[1] == 1){
                    N1.GuiGeHua = true;
                }
                else N1.GuiGeHua = false;
            }

        }







        //接下来需要对结果进行规格化
        if (N1.GuiGeHua){
            return NumReturnString(N1);
        }
        else {
            return NumReturnString(LeftMoveFraction(N1));
        }


    }


    public String Sub(String num1,String num2){
        StringBuilder sb = new StringBuilder(num2);
        if (num2.charAt(0) == '0'){
            sb.replace(0,1,"1");
        }
        else {
            sb.replace(0,1,"0");
        }

        return Addition(num1,sb.toString());

    }



    public String Multiply(String num1 ,String num2){
        float_point_number N1 = new float_point_number(num1);
        float_point_number N2 = new float_point_number(num2);
        if (N1.CheckZero(N1.getNumber())){
            return TurnArraysToString(N1.NEWINT(N1.getNumber().length , 0)).toString();
        }
        else if (N2.CheckZero(N2.getNumber())){
            return TurnArraysToString(N1.NEWINT(N1.getNumber().length , 0)).toString();
        }
        else {
            //阶值相加
            int[] newBias_exponent = N1.binaryAddition(N1.getBias_exponent() , N2.getBias_exponent());
            int[] O127 = {0,1,1,1,1,1,1,1};
            int[] O1023 = {0,1,1,1,1,1,1,1,1,1,1};
            int[] temp = N1.YuanMaJianFa(newBias_exponent,O127);
            if (temp[0] == 0){
                N1.Bias_exponent = N1.Intercept_array(temp,1,temp.length - 1);
                //报告上溢
                if (BiasOverflow(N1)){
                    StringBuilder sb = new StringBuilder();
                    sb.append(N1.getSignBit());
                    for (int i = 0; i < N1.getBias_exponent().length ; i++){
                        sb.append(1);
                    }
                    for (int i = 0 ; i < N1.getFraction().length ; i ++){
                        sb.append(0);
                    }
                    return sb.toString();
                }
                else {
                    M1 m1 = new M1();
                    int[] p = {1};
                    int[] a = N1.Combine(p,N1.getFraction());
                    int[] b = N2.Combine(a,N2.getFraction());
                    int[] tempValue = m1.calculate(a,b);
                    //然后对这个进行移位处理.
                    /*
                    *
                    *
                    *
                    *
                    *
                    *
                     */
                    return null;
                }
            }
            else {
                //报告下溢
                System.out.println("出现下溢");
                return null;
            }
        }
    }









    //实现左移有效值进行规格化
    public float_point_number LeftMoveFraction(float_point_number N){
        int[] Z = {0};
        int[] temp = N.Combine(Z,N.getFraction());
        int count = 23;
        for (int i = 0 ; i < temp.length - 1 ; i ++){
            if (temp[0]!=1) {
                temp[i] = temp[i + 1];
                //阶值减一，注意检查阶值下溢
                N.Bias_exponent = N.BiasBinusOne(N.Bias_exponent);
                if (BiasUnderflow(N)){
                    System.out.println("阶值下溢");
                }
            }
            else break;
        }
        if (N.CheckZero(temp)){
            System.out.println("阶值下溢");
            return null;
        }
        else {
            N.Fraction = N.Intercept_array(temp,1,temp.length - 1);
            return N;
        }
    }


    //把fln对象返回为String
    public String NumReturnString(float_point_number N) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(N.getSignBit());
        stringBuilder1.append(TurnArraysToString(N.getBias_exponent()));
        stringBuilder1.append(TurnArraysToString(N.getFraction()));
        return stringBuilder1.toString();
    }

    public StringBuilder TurnArraysToString(int[] x){
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < x.length ; i++){
            sb.append(x[i]);
        }
        return sb;
    }




    public boolean BiasOverflow(float_point_number N){
        int[] max1 = {0,1,1,1,1,1,1,1};
        int[] max2 = {0,1,1,1,1,1,1,1,1,1,1};
        if (N.getNumber().length == 32){
            if (ArraysJudge(N.getBias_exponent(),max1)){
                return true;
            }
        }
        else if (N.getNumber().length == 64){
            if (ArraysJudge(N.getBias_exponent(),max2)){
                return true;
            }
        }
        else {
            System.out.println("传进来的指数有问题，判断上溢失败");
            return false;
        }
        return false;
    }


    public boolean BiasUnderflow(float_point_number N){
        int[] min1 = {0,0,0,0,0,0,0,0};
        int[] min2 = {0,0,0,0,0,0,0,0,0,0,0};
        if (N.getNumber().length == 32){
            if (ArraysJudge(N.getBias_exponent(),min1)){
                return true;
            }
        }else if (N.getNumber().length == 64){
            if (ArraysJudge(N.getBias_exponent(),min2)){
                return true;
            }
        }
        else {
            System.out.println("传进来的指数有问题,判断下溢失败");
            return false;
        }
        return false;
    }



    public boolean ArraysJudge(int[] a , int[] b){
        if (a.length != b.length){
            return false;
        }
        for (int i = 0 ; i < a.length ; i++){
            if (a[i] != b[i]){
                return false;
            }
        }
        return true;
    }

}
