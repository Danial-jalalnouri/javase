package com.mysite.banking.sampleTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testAddA(){
        Calculator calculator = new Calculator();
        int result = calculator.add(3,5);
        assertEquals(8, result);
    }

    @Test
    public void testAddB(){
        Calculator calculator = new Calculator();
        int result = calculator.add(4,5);
        assertEquals(9, result);
    }


    @Test
    public void testAddC(){
        Calculator calculator = new Calculator();
        int result = calculator.add(1,5);
        assertEquals(6, result);
    }

    @Test
    public void testAddD(){
        Calculator calculator = new Calculator();
        int result = calculator.add(3,1);
        assertEquals(4, result);
    }
}
