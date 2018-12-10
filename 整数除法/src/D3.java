public class D3 {
    /*补码除法算法
    •通过在前面添加n位（具体的位数应该和被除数的位数相等）符号来扩展被除数，并将其存储在余
    数和商寄存器（并不需要单独设出，可以通过与D2相同的方式在被除数移位的过程中，空出来的一位一位的作为商）中
    0.初始化，要先在被除数的前面添加上n（n为被除数的位数）个0或者1（根据原有符号判断，原来最开始的符号位0就加0，为1就加1）
    1.左移剩余部分和商
    2.比较"remainder 和 divisor"
        a.如果remainder比divisor要大，那么就做减法，然后quotient的最后一位设为1
        b.否则，最后一位设为0
    3.重复1-2步，被除数有多少位就重复多少次
    最后余数在remainder中，商在divisor中
    为了处理负数，我们考虑余数定义为D = Q * V + R
     */




    //这里原本应该需要自己写一个初始化程序来生成remainder，但是懒给省了
    int[] remainder = {1,1,1,1};
    int[] quotient = {1,0,0,1};
    int[] divisor = {0,0,1,1};

    public void binaryDIVISION3(){
        for (int i = quotient.length - 1 ; i >= 0 ; i --){
            //对两个寄存器进行移位
            remainder = LEFTMOVE(remainder);
            remainder[remainder.length - 1] = quotient[0];
            quotient = LEFTMOVE(quotient);

            PRINT(remainder);
            PRINT(quotient);
            PRINT(divisor);
            System.out.println();


            if (Comparing(remainder,divisor)){
                if (remainder[0] == divisor[0]){
                    remainder = binarySub(remainder,divisor);
                }
                else {
                    remainder = binaryAddition(remainder,divisor);
                }


                quotient[quotient.length - 1] = 1;
            }
            else {
                quotient[quotient.length - 1] = 0;
            }



            PRINT(remainder);
            PRINT(quotient);
            PRINT(divisor);
            System.out.println();
        }

        //If the dividend has the different sign with divisor, replace quotient with its complement
        if (!(quotient[0] == divisor[0])){
            int[] temp = new int[quotient.length];
            for (int i = 0 ; i < quotient.length ; i ++){
                temp[i] = 0 ;
            }
            temp[quotient.length - 1] = 1;
            for (int i = 0 ; i < quotient.length ; i ++){
                quotient[i] = 1 - quotient[i];
            }
            quotient = binaryAddition(quotient,temp);
        }


        System.out.println("最终的结果是：");
        PRINT(remainder);
        PRINT(quotient);
        PRINT(divisor);
    }




    public void PRINT(int[]a){
        for (int i = 0 ; i < a.length ; i ++){
            System.out.print(a[i]);
        }
        System.out.print(" ");
    }


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





    //左移方法
    public int[] LEFTMOVE(int[] a){
        int[] temp = new int[a.length];
        for (int i = 0 ; i < a.length - 1 ; i ++){
            temp[i] = a[i + 1];
        }
        temp[temp.length - 1] = 0;
        return temp;
    }

    public static void main(String[] args) {
        D3 d3 = new D3();
        d3.binaryDIVISION3();
    }

}



