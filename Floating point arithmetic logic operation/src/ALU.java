public class ALU {
    //这个类用来实现浮点数运算的逻辑
    String number1;
    String number2;
    public ALU(String a , String b){
        this.number1 = a;
        this.number2 = b;
    }

    //实现乘法
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

        可以考虑用for循环来实现"对其有效值"操作
        if (num1.substring(1,9).equals(num2.substring(1,9))){

        }
        */
        return null;
    }



}
