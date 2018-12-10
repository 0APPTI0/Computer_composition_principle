public class M3 {
    int [] ChengShu = {0,0,1,1};
    int [] BeiChengShu = {0,1,1,1};
    //初始化部分积
    int[] BuFenJi = {0,0,0,0};

    //部分积+乘数（临时结果）最右边一位的再往右一位
    int BeiChengShu_1 = 0;




    //布斯算法
    public void boothAddition(){

        for (int count = ChengShu.length-1 ; count >= 0 ; count --) {


            if (ChengShu[ChengShu.length - 1] == 0 && BeiChengShu_1 == 1) {
                //把"乘数"加到"部分积"上
                BuFenJi = binaryAddition(BuFenJi, BeiChengShu);
                BeiChengShu_1 = ChengShu[ChengShu.length - 1];
                yiWei(BuFenJi, ChengShu);
            }

            else if (ChengShu[ChengShu.length - 1] == BeiChengShu_1) {
                BeiChengShu_1 = ChengShu[ChengShu.length - 1];
                yiWei(BuFenJi, ChengShu);
            }

            else if (ChengShu[ChengShu.length - 1] == 1 && BeiChengShu_1 == 0) {
                BuFenJi = binarySub(BuFenJi, BeiChengShu);
                BeiChengShu_1 = ChengShu[ChengShu.length - 1];
                yiWei(BuFenJi, ChengShu);
            }



            
            for (int i = 0 ; i < BuFenJi.length ; i ++){
                System.out.print(BuFenJi[i]);
            }
            System.out.print(' ');
            for (int i = 0 ; i < ChengShu.length ; i ++){
                System.out.print(ChengShu[i]);
            }
            System.out.print(' ');
            System.out.print(BeiChengShu_1);
            System.out.println();
        }

    }




    //移位操作
    public void yiWei(int[] A,int[] B ){
        //先从后面一半开始操作
        for (int count = B.length - 1 ; count >= 1 ; count --){
            B[count] = B[count - 1];
        }
        B[0] = A[A.length - 1];
        for (int count = A.length - 1 ; count >= 1 ; count -- ){
            A[count] = A[count - 1];
        }
        //算数右移，保持符号位不变
        A[0] = A[1];
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

    public int[] binarySub(int[] a , int[] b){
        //b取补码，然后做加法
        int[] temp = {0,0,0,1};
        int[] B = {0,0,0,0};
        for (int count = 0 ; count < b.length ; count ++){
            B[count] = 1 - b[count];
        }
        temp = binaryAddition(B,temp);
        return binaryAddition(a,temp);
    }


    public static void main(String[] args) {
        M3 m3 = new M3();
        m3.boothAddition();

        for (int i = 0 ; i < m3.BuFenJi.length ; i ++){
            System.out.print(m3.BuFenJi[i]);
        }
        System.out.print(" ");
        for (int i = 0 ; i < m3.ChengShu.length ; i ++){
            System.out.print(m3.ChengShu[i]);
        }
    }

}
