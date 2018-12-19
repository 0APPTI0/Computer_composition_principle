package com;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class exchangeTest {

    exchange ec = new exchange();
    @Test
    void strToDou() {
    }

    @Test
    void sToInt() {
    }

    @Test
    void sToDou() {
    }

    @Test
    void douToStr() {
    }

    @Test
    void intToStr() {
    }

    @Test
    void dToStr() {
    }

    @Test
    void STOINT() {
        assertEquals(2,ec.STOINT("10"));
    }

    @Test
    void math_Exchange() {
        assertEquals(ec.strToDou("110.11"),ec.Math_Exchange("110.11"));
    }


    @Test
    void math_Loop() {
        assertEquals("101.1",ec.Math_Loop("5.5"));
    }
}