package com;


public class exchange {

    //把输入的字符串分成整数部分s1和小数部分s2,最终得到一个浮点数
    public String strToDou(String s){
        int n = s.indexOf(".");
        if(n<0){
            s += ".0";
            n = s.indexOf(".");
        }
        String s1 = s.substring(0,n);
        String s2 = s.substring(n+1);
        double a = sToInt(s1) + sToDou(s2);
        return String.valueOf(a);
    }

    //把整数部分转成double
    //递归实现
    public int sToInt(String s){
        if(s.length()==1) return s.charAt(0)-'0';
        return sToInt(s.substring(0,s.length()-1)) * 2 + (s.charAt(s.length()-1)-'0');
    }

    //可以调用库函数，将无符号的二进制字符串转化为int
    public int STOINT(String s){
        s = "0" + s;
        return Integer.parseInt(s,2);
    }

    //把小数部分转成double
    public double sToDou(String s){
        if(s.length()==1) return (s.charAt(0)-'0')/2.0;
        return (s.charAt(0)-'0')/2.0 + sToDou(s.substring(1))/2.0;
    }

    //迭代实现法
    public String Math_Exchange(String s){
        int index = s.indexOf(".");
        String a = s.substring(0,index);
        String b = s.substring(index+1);
        double A = 0.0;
        for (int i = 0 ; i <= a.length() - 1 ; i ++ ){
            if (a.charAt(i) == '1') {
                A = A + Math.pow(2, a.length() - i - 1);
            }
        }
        double B = 0.0;
        for (int j = 0 ; j < b.length() ; j ++){
            if (b.charAt(j)== '1') {
                B = B + Math.pow(2, -(j + 1));
            }
        }

        return String.valueOf(A + B);
    }








    //把输入的浮点数分成整数部分和小数部分，最终得到一个二进制字符串
    public String douToStr(String s){
        int n = s.indexOf(".");
        if(n<0){
            s = s + ".0";
            n = s.indexOf(".");
        }
        String s1 = s.substring(0,n);
        String s2 = "0" + s.substring(n);

        String s3 = intToStr(Integer.parseInt(s1));
        String s4 = dToStr(Double.parseDouble(s2));
        return  (s3.length()==0? 0 : s3) + "." + s4;
    }

    //把整数部分转成二进制
    public String intToStr(int n){
        if(n==0) return "";
        int a = n % 2;
        int b = n / 2;
        return intToStr(b) + a;
    }

    //把小数部分转成二进制
    public String dToStr(double d){
        if(d-(int)d<0.000000001) {
            return "" + (int)d;
        }
        int n = (int)(d * 2);
        double a = d * 2 - n;
        return "" + n + dToStr(a);
    }

    //迭代实现法
    public String Math_Loop(String a){
        int k = a.indexOf(".");
        String s1 = a.substring(0,k);//整数部分
        String s2 = "0" + a.substring(k);//小数部分
        int N = Integer.parseInt(s1);
        double n = Double.parseDouble(s2);
        String L1 = "";
        String L2 = "";
        int i = 0;
        while (true){
            if (Math.pow(2,i) <= N) {
                i += 1;
                continue;
            }
            else break;
        }
        for (; i >= 0 ; i --){
            int temp = (int) Math.pow(2,i);
            if (N >= temp){
                N = N - temp;
                L1 = L1 + "1";
            }
            else if (!L1.equals("")){
                L1 = L1 + "0";
            }

        }

        int counterJudge = 32;
        while (counterJudge >= 0){
            n = 2 * n;
            if (n > 1){
                L2 = L2+"1";
                n = n - 1;
            }
            else if (n == 1){
                L2 += "1";
                break;
            }
            else L2 = L2 + "0";
        }

        return L1+"."+L2;
    }

}