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

    //把小数部分转成double
    public double sToDou(String s){
        if(s.length()==1) return (s.charAt(0)-'0')/2.0;
        return (s.charAt(0)-'0')/2.0 + sToDou(s.substring(1))/2.0;
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
        if(d-(int)d<0.01) return "" + (int)d;
        int n = (int)(d * 2);
        double a = d * 2 - n;
        return "" + n + dToStr(a);
    }


}