public class D1 {
    //无符号的整数除法的常规算法

    //被除数，也用来储存部分余
    int[] Remainder = {0,0,0,0,0,1,1,1};

    // the divisor is less than the number of divisors, then it is necessary to make up（补齐）.
    //除数
    int[] Divisor = {0,0,1,1};
    int[] initializedDivisor = new int[Remainder.length];

    //商的寄存器
    int[] Quotient = {0,0,0,0};

    public void BinaryDivision(){
        InitializeDivisor();
        for (int count = Divisor.length - 1 ; count >= 0 ; count --){
            //先左移，然后判断是否够减
            left_moving();
            if (JudgeLarger(Remainder,initializedDivisor)){
                Remainder = binarySub(Remainder,initializedDivisor);
                Quotient_right_moving();
                Quotient[Quotient.length - 1] = 1;
            }
            else Quotient_right_moving();


            PRINT(Remainder);
            PRINT(initializedDivisor);
            PRINT(Quotient);
            System.out.println();

        }

    }


    public void PRINT(int[]a){
        for (int i = 0 ; i < a.length ; i ++){
            System.out.print(a[i]);
        }
        System.out.print(" ");
    }




    //if a is larger than b , return true ; other way , return false
    public boolean JudgeLarger (int[] a , int[] b){
        //如果a和b的符号位相等，那就进行相减操作（a - b）；得到的结果如果和原来的符号相等，那么就是a大；否则就是b大
        int[] temp = new int[a.length];
        if (a[0] == b[0]){
            temp = binarySub(a, b);
            if (temp[0] == a[0]){
                return true;
            }
            else return false;
        }
        else if (a[0] == 0 && b[0] == 1){
            return true;
        }
        else {
            return false;
        }
    }

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
    public int[] binaryAddition(int[] a , int[] b){
        int JiLuJingWei = 0;
        int[] result = new int[a.length];
        for (int count = a.length - 1 ; count >=0 ; count --){
            //三个中有两个1，产生了进位
            if ((a[count] == 1 && b[count] == 1) || (a[count] == 1 && JiLuJingWei == 1) || (b[count] == 1 && JiLuJingWei == 1) ){
                result[count] = a[count] + b[count] + JiLuJingWei - 2;
                JiLuJingWei = 1;
            }
            else {
                result[count] = a[count] + b[count] + JiLuJingWei;
                JiLuJingWei = 0;
            }
        }
        return result;
    }



    //商的左移，最右边补上0
    public void Quotient_right_moving(){
        for (int i = 0 ; i < Quotient.length - 1 ; i ++){
            Quotient[i] = Quotient[i + 1];
        }
        Quotient[Quotient.length - 1] = 0;
    }

    //除数的右移,最左边补上0
    public void left_moving(){
        for (int i = initializedDivisor.length - 1 ; i >= 1 ; i --){
            initializedDivisor[i] = initializedDivisor[i - 1];
        }
        initializedDivisor[0] = 0;
    }



    public void InitializeDivisor(){
        int t = Remainder.length - Divisor.length;
        if (t > 0) {
            for (int count = 0 ; count < Divisor.length ; count++){
                initializedDivisor[count] = Divisor[count];
            }
            for (int count = Divisor.length ; count < initializedDivisor.length; count ++){
                initializedDivisor[count] = 0;
            }
        }
        else {
            initializedDivisor = Divisor;
        }
    }





    public static void main(String[] args) {
        D1 di = new D1();
        di.BinaryDivision();
    }
}
