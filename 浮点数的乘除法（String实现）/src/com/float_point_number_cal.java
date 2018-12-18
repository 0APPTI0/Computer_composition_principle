package com;

public class float_point_number_cal {

    int Size_of_Expo = 8;
    int Size_of_Base = 23;
    public String Multiply(String number1 , String number2){
        String symbol_of_num1 = String.valueOf(number1.charAt(0));
        String symbol_of_num2 = String.valueOf(number2.charAt(0));
        String symbol;
        if (symbol_of_num1.equals(symbol_of_num2)){
            symbol = "0";
        }
        else symbol = "1";

        String Expo1 = number1.substring(1,Size_of_Base);
        String Expo2 = number2.substring(1,Size_of_Base);
        String Expo = Add(MakeOffset(Size_of_Expo),Sub(Expo1,Expo2))[1];

        String real_Base1 = "01" + number1.substring(Size_of_Base);
        String real_Base2 = "01" + number2.substring(Size_of_Base);


        if (Expo.equals(MakeOffset(Expo.length()))){
            System.out.println("发生了阶值上溢");
            return symbol + Expo + MakeOffset()
        }





        return null;
    }


    //@return String[] , result[0]存放加法是否产生进位，result[1]存放运算结果
    public String[] Add(String num1 , String num2){
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

    public String Sub(String num1 , String num2){
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
    public String MakeOffset(int length){
        String result = "0";
        for (int i = 1 ; i <= length - 1 ; i ++){
            result = result + "1";
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

    //判断字符串是否全是0
    public boolean isAllZero(String s){
        for (int i = 0 ; i < s.length() ; i ++){
            if (s.charAt(i) == '1'){
                return false;
            }
        }
        return true;
    }

    //判断字符串是否全是1
    public boolean isAllOne(String s){
        for (int i = 0 ; i < s.length() ; i ++){
            if (s.charAt(i) == '0'){
                return false;
            }
        }
        return true;
    }

    //左移字符串
    public String LeftMove (String a){
        return a.substring(0,a.length() - 1)+"0";
    }

    //算数右移字符串,左边直接添加1
    public String RightMove_with_1(String a){
        return ("1"+a).substring(0,a.length());
    }

    //逻辑右移字符串（左边直接添加0
    public String RightMove(String a){
        return ("0" + a).substring(0,a.length());
    }

    public String Ing_Mul(String num1 , String num2){
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

    public static void main(String[] args) {
        float_point_number_cal f2 = new float_point_number_cal();
        System.out.println(f2.Ing_Mul("1101","1011"));
    }

}
