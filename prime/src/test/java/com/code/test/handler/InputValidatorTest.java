package com.code.test.handler;

import org.junit.Before;
import org.junit.Test;

public class InputValidatorTest {

    private InputHandler inputHandler;

    @Before
    public void setUp() {
        inputHandler = new InputValidator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        inputHandler.process(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCcy() {
        inputHandler.process("A 100");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAmount() {
        inputHandler.process("USD 1A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormat() {
        inputHandler.process("USD USD 1A");
    }

    @Test
    public void testAllGood() {
        inputHandler.process("USD 100");
        inputHandler.process("USD -100");
        inputHandler.process("USD -10.0");
    }
}