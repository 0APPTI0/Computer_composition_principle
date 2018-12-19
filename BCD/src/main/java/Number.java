public class Number {

    int defaultLength = 10;

    public String positive = "1100";

    public String negative = "1101";

    //表示进位的符号，用完之后要手动设为false
    public boolean bitCarry = false;

    public String symbol ;

    public String[] Absolute_value  = new String[defaultLength];

    //初始化方法
    public Number(String a){
        String temp;
        if (a.charAt(0) == '-'){
            this.symbol = negative;
            temp = a.substring(1);
        }
        else if (a.charAt(0) == '+'){
            temp = a.substring(1);
        }
        else {
            this.symbol = positive;
            temp = a;
        }

        this.defaultLength = temp.length();

        char[] numbers = temp.toCharArray();

        for (int i = 0 ; i < numbers.length ; i ++){
            int Ibit = numbers[i] - '0';
            String bit = Integer.toBinaryString(Ibit);
            Absolute_value[i] = bit;
        }

    }

    //@return String[] , result[0]存放加法是否产生进位，result[1]存放运算结果
    public String[] Addition(String num1, String num2){
        String result = "";
        int carry = 0;
        for (int i = num1.length() - 1 ; i >= 0 ; i --){
            int a = num1.charAt(i) - '0';
            int b = num2.charAt(i) - '0';
            int temp = a + b + carry;
            carry = temp / 2;
            temp = temp % 2;
            result = temp + result;
        }
        String[] R = new String[2];
        R[1] = result;
        R[0] = String.valueOf(carry);
        if (R[0]=="1"){
            this.bitCarry = true;
        }
        return R;
    }

    //减法,在reverse里面会用到
    public String Subtraction(String a , String b){
        int[] temp = new int[a.length()];
        for (int i = 0 ; i < temp.length ; i++){
            temp[i] = 0;
        }
        temp[temp.length - 1] = 1;
        char[] C = new char[b.length()];
        char[] B = b.toCharArray();
        for (int i = 0 ; i < B.length ; i ++){
            if (B[i] == '1'){
                C[i] = '0';
            }
            else C[i] = '1';
        }
        return Addition(C.toString(),temp.toString())[1];
    }



    //数与数之间的加法.最终结果加到a1上面
    public Number ADD(Number a1 , Number a2){
        for (int i = a1.Absolute_value.length - 1 ; i >=0 ; i --){
            if (a1.bitCarry){
                a1.bitCarry = false;
                a1.Absolute_value[i] = a1.Addition(a1.Absolute_value[i],"0001")[1];
                a1.Absolute_value[i] = a1.Addition(a1.Absolute_value[i],a2.Absolute_value[i])[1];
            }
            else {
                a1.Absolute_value[i] = a1.Addition(a1.Absolute_value[i],a2.Absolute_value[i])[1];
            }
        }
        return a1;
    }

    //所谓取反加一
    public Number reverse(Number n1){
        //取反
        for (int i = 0 ; i < n1.Absolute_value.length ; i++){
            n1.Absolute_value[i] = n1.Subtraction("1001",n1.Absolute_value[i]);
        }
        //制造一个"0000001"
        Number zore = new Number("0");
        String[] temp = new String[n1.Absolute_value.length];
        for (int i = 0 ; i < temp.length - 1 ; i ++){
            temp[i] = "0000";
        }
        temp[temp.length - 1] = "0001";
        zore.Absolute_value = temp;
        //加一
        return n1.ADD(n1,zore);
    }

    //加6（"0110"）按位取反
    public Number Handle(Number a){
        for (int i = 0 ; i < a.Absolute_value.length - 1 ; i ++){
            Absolute_value[i] = Addition(Absolute_value[i],"0110")[1];
            char[] temp = Absolute_value[i].toCharArray();
            for (char x:temp){
                if (x == '0'){
                    x = '1';
                }
                else x = '0';
            }
            Absolute_value[i] = temp.toString();
        }
        //为了避免在这里产生进位方面的影响
        a.bitCarry = false;

        return a;

    }

}
