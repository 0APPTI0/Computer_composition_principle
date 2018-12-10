public class D2 {
    //无符号整数的改进算法 为了节省空间，省去了专门的"商"（Quotient）的数组
    //the size of remainder must be the same as the divisor
    int[] remainder = {0,0,0,0};
    int[] quotient = {0,1,1,1};
    int[] divisor = {0,0,1,1};

    public void BinaryDivision2(){
        for (int i = 0 ; i < quotient.length ; i ++) {
            remainder = LEFTMOVE(remainder);
            remainder[remainder.length - 1] = quotient[0];
            quotient = LEFTMOVE(quotient);
            if (JudgeLarger(remainder,divisor)){
                remainder = binarySub(remainder,divisor);
                quotient[quotient.length - 1] = 1;
            }


            PRINT(remainder);
            PRINT(quotient);
            PRINT(divisor);
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
        D2 d2 = new D2();
        d2.BinaryDivision2();
    }
}
