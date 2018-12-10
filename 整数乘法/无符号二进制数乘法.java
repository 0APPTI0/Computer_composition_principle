public class M1 {
    //无符号数的乘法
    //用于保存"是否进位"
    int JingWei = 0;
    //乘数
    int[] ChengShu = {1,1,0,1};
    //被乘数
    int[] BeiChengShu = {1,0,1,1};
    //部分积
    int[] BuFenJi = {0,0,0,0};

    public int[] calculate(){
        for (int count = ChengShu.length - 1 ; count >= 0 ; count --){
            if (ChengShu[ChengShu.length - 1] == 1){
                BuFenJi = binaryAddition(BeiChengShu,BuFenJi);
                yiWei(BuFenJi,ChengShu);
                this.JingWei = 0;
            }
            else {
                yiWei(BuFenJi,ChengShu);
            }


/*
            for (int i = 0 ; i < BuFenJi.length ; i ++){
                System.out.print(BuFenJi[i]);
            }
            System.out.print(' ');
            for (int i = 0 ; i < ChengShu.length ; i ++){
                System.out.print(ChengShu[i]);
            }
            System.out.println();
            */

        }

        //此处应该合并部分积和乘数两个数组
        int[] result = new int[BuFenJi.length + ChengShu.length];
        for (int i = 0 ; i < BuFenJi.length ; i ++){
            result[i] = BuFenJi[i];
        }
        for (int i = 4 ; i < ChengShu.length + 4 ; i ++){
            result[i] = ChengShu[i - 4];
        }
        return result;
    }

    //移位操作，如果进位位为1，则左侧补上1，入股进位位为0，则左侧补上0；注意要把
    public void yiWei(int[] A,int[] B ){
        //先从后面一半开始操作
        for (int count = B.length - 1 ; count >= 1 ; count --){
            B[count] = B[count - 1];
        }
        B[0] = A[A.length - 1];
        for (int count = A.length - 1 ; count >= 1 ; count -- ){
            A[count] = A[count - 1];
        }
        if (this.JingWei == 1){
            A[0] = 1;
        }
        else A[0] = 0;
    }





    //二进制加法
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
        if (JiLuJingWei == 1){
            this.JingWei = 1;
        }
        return result;
    }






    public static void main(String[] args) {

        M1 m1 = new M1();



        int[] k = m1.calculate();
        for (int x: k){
            System.out.print(x);

        }

    }
}



