package com;

public class float_point_number_cal {

    private int Size_of_Expo = 8;
    private int Size_of_Base = 23;
    public String Multiply(String number1 , String number2){
        String symbol_of_num1;
        symbol_of_num1 = String.valueOf(number1.charAt(0));
        String symbol_of_num2;
        symbol_of_num2 = String.valueOf(number2.charAt(0));
        String symbol;
        if (symbol_of_num1.equals(symbol_of_num2)){
            symbol = "0";
        }
        else symbol = "1";

        String Expo1;
        Expo1 = number1.substring(1,Size_of_Base);
        String Expo2 = number2.substring(1,Size_of_Base);
        String Expo = Add(MakeOffset(Size_of_Expo),Sub(Expo1,Expo2))[1];

        String real_Base1 = "01" + number1.substring(Size_of_Base);
        String real_Base2 = "01" + number2.substring(Size_of_Base);
        String Base = new String();

        if (Expo.equals(MakeAllOne(Expo.length()))){
            System.out.println("发生了阶值上溢");
            return symbol + Expo + MakeOffset(Size_of_Base);
        }
        if (Expo.equals(MakeZero(Size_of_Expo))){
            System.out.println("发生了阶值下溢");
            return symbol + Expo + MakeZero(Size_of_Base);
        }

        //小数点应该在第四位之后，按照这个规则来截取
        String unHandledValue = Multiply(real_Base1,real_Base2);

        int index = getFirstOne(unHandledValue);
        //如果左边三位出现了"1"，就需要对这个值进行右移，直到最左边的1
        if (index < 3){
            //进行右移，直到这个数第四个char为"1"；随后指数要进行减一。
            while(unHandledValue.charAt(3) != '1'){
                unHandledValue = RightMove(unHandledValue);
                Expo = Sub(Expo,MakeLastOne(Size_of_Expo));
                if (Expo.equals(MakeZero(Size_of_Expo))){
                    System.out.println("发生了阶值下溢");
                    return symbol + Expo + MakeZero(Size_of_Base);
                }
            }
            Base = unHandledValue.substring(4,4 + Size_of_Base);
            return symbol + Expo + Base;
        }
        else if (index == 3){
            //直接进行截取，然后返回
            Base = unHandledValue.substring(4,4 + Size_of_Base);
            return symbol + Expo + Base;
        }
        else {
            //左移，直到这个数第四个char为"1"；随后指数要进行加一。
            while (unHandledValue.charAt(3) != '1'){
                unHandledValue = LeftMove(unHandledValue);
                Expo = Add(Expo,MakeLastOne(Size_of_Expo))[1];
                if (Expo.equals(MakeAllOne(Size_of_Expo))){
                    System.out.println("发生了阶值上溢");
                    return symbol + Expo + MakeOffset(Size_of_Base);
                }
            }
            Base = unHandledValue.substring(4,4 + Size_of_Base);
            return symbol + Expo + Base;
        }

    }

    public String Div(String number1 , String number2){
        String symbol_of_num1 = String.valueOf(number1.charAt(0));
        String symbol_of_num2 = String.valueOf(number2.charAt(0));
        String symbol;
        if (symbol_of_num1.equals(symbol_of_num2)){
            symbol = "0";
        }
        else symbol = "1";

        String Expo1 = number1.substring(1,Size_of_Base);
        String Expo2 = number2.substring(1,Size_of_Base);
        String Expo = Sub(Add(Expo1,Expo2)[1],MakeOffset(Size_of_Expo));

        String real_Base1 = "01" + number1.substring(Size_of_Base);
        String real_Base2 = "01" + number2.substring(Size_of_Base);
        String Base = new String();
        if (Expo.equals(MakeAllOne(Expo.length()))){
            System.out.println("发生了阶值上溢");
            return symbol + Expo + MakeOffset(Size_of_Base);
        }
        if (Expo.equals(MakeZero(Size_of_Expo))){
            System.out.println("发生了阶值下溢");
            return symbol + Expo + MakeZero(Size_of_Base);
        }

        return null;

    }

    //返回一个字符串第一个"1"的位置
    private int getFirstOne(String s){
        for (int i = 0 ; i < s.length() ; i ++){
            if (s.charAt(i) == '1'){
                return i;
            }
        }
        return 99;
    }

    //@return String[] , result[0]存放加法是否产生进位，result[1]存放运算结果
    public String[] Add(String num1, String num2){
        String result = "";
        int carry = 0;
        for (int i = num1.length() - 1 ; i >= 0 ; i --){
            int a = num1.charAt(i) - '0';
            int b = num2.charAt(i) - '0';
            int temp = a + b + carry;
            carry = temp / 2;
            temp = temp % 2;
            result = String.valueOf(temp) + result;
        }
        String[] R = new String[2];
        R[1] = result;
        R[0] = String.valueOf(carry);
        return R;
    }

    public String Sub(String num1, String num2){
        return Add(num1 , Add(reverse(num2),MakeOne(num2.length()))[1])[1];
    }

    //将字符串取反
    public String reverse(String a){
        String result = "";
        for (int i = a.length() - 1 ; i >= 0 ; i --){
            int temp = 1 - (a.charAt(i) - '0');
            result = temp + result;
        }
        return result;
    }

    //造出"01111111"
    private String MakeOffset(int length){
        String result = "0";
        for (int i = 1 ; i <= length - 1 ; i ++){
            result = result + "1";
        }
        return result;
    }

    //造出"0000000000"
    private String MakeZero(int a ){
        String result = "";
        for (int i = 0 ; i < a ; i ++){
            result = "0" + result;
        }
        return result;
    }

    //造出 "0000000000001"
    public String MakeOne(int i){
        String result = "";
        for (int a = 0 ; a < i - 1 ; a ++){
            result = "0" + result;
        }
        result = result + "1";
        return result;
    }

    //造出"00000000000001"
    public String MakeLastOne(int i){
        String result = "1";
        for (int a = 0 ; a < i - 1 ; a ++){
            result = "0" + result;
        }
        return result;
    }

    //造出"1111111111"
    private String MakeAllOne(int a){
        String result = "";
        for (int i = 0 ; i < a ; i ++){
            result = "1" + result;
        }
        return result;
    }

    //判断字符串是否全是0
    private boolean isAllZero(String s){
        for (int i = 0 ; i < s.length() ; i ++){
            if (s.charAt(i) == '1'){
                return false;
            }
        }
        return true;
    }

    //判断字符串是否全是1
    private boolean isAllOne(String s){
        for (int i = 0 ; i < s.length() ; i ++){
            if (s.charAt(i) == '0'){
                return false;
            }
        }
        return true;
    }

    //左移字符串
    private String LeftMove (String a){
        return a.substring(1)+"0";
    }

    //算数右移字符串,左边直接添加1
    private String RightMove_with_1(String a){
        return ("1"+a).substring(0,a.length());
    }

    //逻辑右移字符串（左边直接添加0
    public String RightMove(String a){
        return ("0" + a).substring(0,a.length());
    }

    public String Ing_Mul(String num1, String num2){
        String A = MakeOne(num1.length()).substring(0,num1.length() - 1) + "0";
        String temp = "";
        String C = "0";
        for (int i = num1.length() - 1 ; i >= 0 ; i --){
            //先做加法
            if (num1.charAt(num1.length() - 1) == '1') {
                String[] T;
                T = Add(A, num2);
                A = T[1];
                C = T[0];
            }
            //移位
            temp = A + num1;


            if (C.equals("0")) {
                temp = RightMove(temp);
            }
            else {
                temp = RightMove_with_1(temp);
            }
            A = temp.substring(0,A.length());
            num1 = temp.substring(A.length());
        }
        return temp;
    }

    public String Ing_Del(String num1 , String num2){
        /*
        •通过在前面添加n位符号来扩展被除数，并将其存储在余数和商寄存器中
        •左移剩余部分和商，并判断余数是否足够大
        •如果足够大，请执行加法或减法，并将商设置为1
        •如果不够大，请将商设为0
        •重复上述步骤
        •如果被除数与除数有不同的符号，则用其补码替换商
        •余数在余数寄存器中
         */
        String operateNumber;
        if (num1.charAt(0) == '0') {
            operateNumber= MakeZero(num1.length()) + num1;
        }
        else operateNumber = MakeAllOne(num1.length()) + num1;

        for (int i = 0 ; i < num1.length() ; i ++){
            operateNumber = LeftMove(operateNumber);

            String ON1 = operateNumber.substring(0,num1.length());
            String ON2 = operateNumber.substring(num1.length());
            if (Compare(ON1,num2)){
                if (ON1.charAt(0) == num2.charAt(0)){
                    ON1 = Sub(ON1,num2);
                }
                else {
                    ON1 = Add(ON1,num2)[1];
                }
                operateNumber = (ON1 + ON2).substring(0,operateNumber.length() - 1);
                operateNumber = operateNumber+"1";

            }
        }

        return operateNumber;
    }

    //二进制补码之间比较大小
    private boolean Compare(String a , String b){
        String temp;
        if (a.charAt(0) == b.charAt(0)){
            temp = Sub(a,b);
            if (temp.charAt(0) == a.charAt(0))
                return true;
            else return false;
        }
        else {
            temp = Add(a,b)[1];
            if (temp.charAt(0) == a.charAt(0))
                return true;
            else return false;
        }
    }

    public static void main(String[] args) {
        float_point_number_cal f2 = new float_point_number_cal();
        System.out.println(f2.Ing_Mul("1101","1011"));
    }

}
