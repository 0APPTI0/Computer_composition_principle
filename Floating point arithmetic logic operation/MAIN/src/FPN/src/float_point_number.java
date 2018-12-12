package FPN.src;

import java.util.Arrays;

public class float_point_number {
    //这个类用来表示浮点数以及浮点数之间的一些操作（加减乘除以及一些辅助操作）

    //浮点数数本身
    int[] number ;
    public int[] getNumber() {
        return number;
    }

    //浮点数的符号位
    int signBit ;
    public int getSignBit(){
        return signBit;
    }

    //浮点数的移码阶值
    int[] Bias_exponent;
    public int[] getBias_exponent() {
        return Bias_exponent;
    }

    //浮点数的有效值
    int[] Fraction;
    public int[] getFraction() {
        return Fraction;
    }

    //构造函数，顺带初始化
    public float_point_number(String origin){
        char[] temp = origin.toCharArray();
        int[] a = new int[temp.length];
        for (int i = 0 ; i < temp.length ; i ++){
            if (temp[i] == '1'){
                a[i] = 1;
            }
            else if (temp[i] == '0'){
                a[i] = 0;
            }
        }
        this.number = a;
        if (a.length == 32){
            signBit = a[0];
            Bias_exponent = Intercept_array(a,1,8);
            Fraction = Intercept_array(a,9,31);
        }
        else if (a.length == 64){
            signBit = a[0];
            Bias_exponent = Intercept_array(a,1,11);
            Fraction = Intercept_array(a,12,61);
        }
        else {
            System.out.println("这是一个错误的浮点数");
        }
    }




    //检查是否为0
    public boolean CheckZero(int[] a){
        for (int i = 1 ; i < a.length ; i ++){
            if (a[i] == 1){
                return false;
            }
        }
        return true;
    }


    //二进制补码的加法 这里默认两个相加的数位数都是相等的
    //相加的话，要另外考虑overflow的情况
    public int[] binaryAddition(int[] a , int[] b){
        int carry = 0;
        int[] result = new int[a.length];
        for (int count = a.length - 1 ; count >=0 ; count --){
            //三个中有两个1，产生了进位
            if ((a[count] == 1 && b[count] == 1) || (a[count] == 1 && carry == 1) || (b[count] == 1 && carry == 1) ){
                result[count] = a[count] + b[count] + carry - 2;
                carry = 1;
            }
            else {
                result[count] = a[count] + b[count] + carry;
                carry = 0;
            }
        }
        return result;
    }

    //二进制补码的减法
    public int[] binarySub(int[] a , int[] b){
        //b取补码，然后做加法
        int[] temp = new int[a.length];
        int[] B = new int[a.length];
        for (int i = 0 ; i < a.length ; i ++){
            temp[i] = 0 ;
            B[i] = 0 ;
        }
        temp[a.length - 1] = 1;

        for (int count = 0 ; count < b.length ; count ++){
            B[count] = 1 - b[count];
        }
        temp = binaryAddition(B,temp);
        return binaryAddition(a,temp);
    }

    //二进制补码的乘法（布斯算法）
    public int[] binaryMultiplication(int[] BeiChengShu , int[] ChengShu){
        int BeiChengShu_1 = 0;
        //初始化一个部分积
        int[] BuFenJi = new int[ChengShu.length];
        for (int i = 0 ; i < BuFenJi.length ; i ++){
            BuFenJi[i] = 0;
        }
        if (ChengShu[ChengShu.length - 1] == 0 && BeiChengShu_1 == 1) {
            //把"乘数"加到"部分积"上
            BuFenJi = binaryAddition(BuFenJi, BeiChengShu);
            BeiChengShu_1 = ChengShu[ChengShu.length - 1];
            ChengShu = Arithmetic_right_shift(ChengShu);
            ChengShu[0] = BuFenJi[BuFenJi.length - 1];
            BuFenJi = Arithmetic_right_shift(BuFenJi);
        }

        else if (ChengShu[ChengShu.length - 1] == BeiChengShu_1) {
            BeiChengShu_1 = ChengShu[ChengShu.length - 1];
            ChengShu = Arithmetic_right_shift(ChengShu);
            ChengShu[0] = BuFenJi[BuFenJi.length - 1];
            BuFenJi = Arithmetic_right_shift(BuFenJi);

        }

        else if (ChengShu[ChengShu.length - 1] == 1 && BeiChengShu_1 == 0) {
            BuFenJi = binarySub(BuFenJi, BeiChengShu);
            BeiChengShu_1 = ChengShu[ChengShu.length - 1];
            ChengShu = Arithmetic_right_shift(ChengShu);
            ChengShu[0] = BuFenJi[BuFenJi.length - 1];
            BuFenJi = Arithmetic_right_shift(BuFenJi);
        }

        return Combine(BuFenJi,ChengShu);
    }



    //二进制的除法实现

    public int[] binaryDIVISION3(int[] quotient , int[] divisor) {
        //生成remainder
        int[] remainder = NEWINT(quotient.length , quotient[0]);
        for (int i = quotient.length - 1; i >= 0; i--) {
            //对两个寄存器进行移位
            remainder = LEFTMOVE(remainder);
            remainder[remainder.length - 1] = quotient[0];
            quotient = LEFTMOVE(quotient);


            if (Comparing(remainder, divisor)) {
                if (remainder[0] == divisor[0]) {
                    remainder = binarySub(remainder, divisor);
                } else {
                    remainder = binaryAddition(remainder, divisor);
                }


                quotient[quotient.length - 1] = 1;
            } else {
                quotient[quotient.length - 1] = 0;
            }

        }

        //If the dividend has the different sign with divisor, replace quotient with its complement
        if (!(quotient[0] == divisor[0])) {
            int[] temp = new int[quotient.length];
            for (int i = 0; i < quotient.length; i++) {
                temp[i] = 0;
            }
            temp[quotient.length - 1] = 1;
            for (int i = 0; i < quotient.length; i++) {
                quotient[i] = 1 - quotient[i];
            }
            quotient = binaryAddition(quotient, temp);
        }
        //商保存在quotient中，余数保存在remainder中
        return Combine(remainder,quotient);
    }

    //比较两个数组表示的二进制数字的大小
    public boolean Comparing(int[] a , int[] b){
        if (a[0] == b[0]){
            int[] temp = binarySub(a, b);
            if (temp[0] == a[0]){
                return true;
            }
            else return false;
        }
        else {
            int[] temp = binaryAddition(a, b);
            if (temp[0] == a[0]){
                return true;
            }
            else return false;
        }
    }



    //算数右移，保持数的符号不变
    public int[] Arithmetic_right_shift(int[] A){
        for (int count = A.length - 1 ; count >= 1 ; count -- ){
            A[count] = A[count - 1];
        }
        //算数右移，保持符号位不变
        A[0] = A[1];
        return A;
    }

    //逻辑右移，最左端补上0
    public int[] RIGHTMOVE(int[] A){
        for (int count = A.length - 1 ; count >= 1 ; count -- ){
            A[count] = A[count - 1];
        }
        //算数右移，保持符号位不变
        A[0] = 0;
        return A;
    }

    //左移方法，右端补上0
    public int[] LEFTMOVE(int[] a){
        int[] temp = new int[a.length];
        for (int i = 0 ; i < a.length - 1 ; i ++){
            temp[i] = a[i + 1];
        }
        temp[temp.length - 1] = 0;
        return temp;
    }



    //截取数组的方法截取的时候包含起始位置的字符和结束位置的字符
    public int[] Intercept_array(int[] a , int x , int y){
        int[] temp = new int[y - x + 1];
        for (int i = 0 ; i < (y - x + 1) ; i ++){
            temp[i] = a[i + x];
        }
        return temp;
    }

    //合并两个数组的方法
    public int[] Combine(int[] a , int[] b){
        int[] temp = new int[a.length + b.length];
        for (int i = 0 ; i < a.length ; i ++){
            temp[i] = a[i];
        }
        for (int j = a.length ; j < temp.length ; j ++){
            temp[j] = b[j - a.length];
        }
        return temp;
    }

    //初始化一个全部为0的数组
    public int[] NEWINT(int length , int a){
        int[] temp = new int[length];
        for (int i = 0 ; i < length ; i ++){
            temp[i] = a;
        }
        return temp;
    }


    public void PRINT(int[] a){
        for (int i = 0 ; i < a.length ; i ++){
            System.out.print(a[i]);
        }
    }


}
