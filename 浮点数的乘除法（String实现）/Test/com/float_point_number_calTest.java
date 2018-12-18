package com;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class float_point_number_calTest {

    float_point_number_cal f1 = new float_point_number_cal();
    @Test
    void add() {
        assertEquals("1101",f1.Add("1001","0100"));
    }

    @Test
    void sub() {
        assertEquals("01",f1.Sub("10","01"));
    }

    @Test
    void ing_Mul() {
        assertEquals("10001111",f1.Ing_Mul("1101","1011"));
    }

    @Test
    void reverse() {
        assertEquals("1001",f1.reverse("0110"));
    }

    @Test
    void makeOne() {
        assertEquals("0001",f1.MakeOne(4));
    }

    @Test
    void leftMove() {
    }

    @Test
    void logic_RightMove() {
    }

    @Test
    void rightMove() {
        assertEquals("0101",f1.RightMove("1010"));
    }

    @Test
    void makeLastOne() {
        assertEquals("00000001",f1.MakeLastOne(8));
    }
}