package FPN.src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class float_point_numberTest {

    //创建一个浮点数对象
    float_point_number fpn0 = new float_point_number("000000000000000000000000000000000");

    @Test
    void checkZero() {
    }

    @Test
    void binaryAddition() {
    }

    @Test
    void binarySub() {
    }

    @Test
    void binaryMultiplication() {
    }

    @Test
    void binaryDIVISION3() {
    }

    @Test
    void comparing() {
    }

    @Test
    void arithmetic_right_shift() {
    }

    @Test
    void RIGHTMOVE() {
    }

    @Test
    void LEFTMOVE() {
    }

    @Test
    void intercept_array() {
        int[] a = {1,2,3,4,5,6};
        int[] b = {1,2};
        assertEquals(true,JudegArray(fpn0.Intercept_array(a,0,1),b));

    }

    @Test
    void combine() {
    }

    @Test
    void NEWINT() {
    }


    //判断两个数组是否相等的方法
    public boolean JudegArray(int[] a , int[] b){
        if (a.length != b.length){
            return false;
        }
        else {
            for (int i = 0 ; i < a.length ; i ++){
                if (a[i] != b[i]){
                    return false;
                }
            }
            return true;
        }
    }
}