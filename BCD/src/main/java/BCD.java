
public class BCD {


    Number num1 = new Number("111");
    Number num2 = new Number("111");


    public Number ADDITION(Number a1, Number a2){
        return a1.ADD(a1,a2);
    }


    //这里只做正数之间的减法
    public Number SUBTION(Number a1 , Number a2){
        //a2先每一位加6取反
        a2 = a2.Handle(a2);
        //然后做加法
        a1 = a1.ADD(a1,a2);
        //然后检查结果
        if (a1.bitCarry){
            return a1;
        }
        else {
            return a1.reverse(a1);
        }
    }


}
