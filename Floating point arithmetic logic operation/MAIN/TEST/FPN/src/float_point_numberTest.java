package FPN.src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class float_point_numberTest {

    //创建一个浮点数对象
    float_point_number fpn0 = new float_point_number("000000000000000000000000000000000");

    @Test
    void checkZerotest() {
        int[] a = fpn0.NEWINT(32,0);
        int[] b = a;
        b[0] = 1;
        assertEquals(true,fpn0.CheckZero(a));
        assertEquals(true,fpn0.CheckZero(b));
    }

    @Test
    void binaryAdditiontest() {
    }

    @Test
    void binarySubtest() {
    }

    @Test
    void binaryMultiplicationtest() {
    }

    @Test
    void binaryDIVISION3test() {
    }

    @Test
    void Bias_exponent_Comparingtest(){
        int[] a = {1,0,0,0,0,0,1,1};
        int[] b = {0,1,1,1,1,1,1,1};
        assertEquals(true,fpn0.Bias_exponent_Comparing(a,b));
    }


    @Test
    void comparingtest() {
    }

    @Test
    void arithmetic_right_shift() {
        int[] a = {1,1,1,0,0,1};
        int[] b = {1,1,0,0,1,1};
        assertArrayEquals(a,fpn0.Arithmetic_right_shift(b));
    }

    @Test
    void RIGHTMOVEtest() {
        int[] a = {0,1,1,0,0,1};
        int[] b = {1,1,0,0,1,1};
        assertArrayEquals(a,fpn0.RIGHTMOVE(b));
    }

    @Test
    void LEFTMOVEtest() {
        int[] b = {1,1,0,0,1,1};
        int[] a = {1,0,0,1,1,0};
        assertArrayEquals(a,fpn0.LEFTMOVE(b));
    }

    @Test
    void intercept_arraytest() {
        int[] a = {1,2,3,4,5,6};
        int[] b = {1,2};
        assertArrayEquals(fpn0.Intercept_array(a,0,1),b);
    }

    @Test
    void combinetest() {
        int[] a = {1,2,3,4,5,6};
        int[] b = {1,2};
        int[] c = {3,4,5,6};
        assertArrayEquals(a,fpn0.Combine(b,c));
    }

    @Test
    void NEWINTtest() {
        int[] a = {1,1,1,1,1};
        assertArrayEquals(a,fpn0.NEWINT(5,1));
    }


}