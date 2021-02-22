package com.code.test.handler;

import java.math.BigDecimal;

public class InputValidator implements InputHandler {

    @Override
    public void process(final String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input can't be null");
        }

        final String[] values = input.split(" ");
        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid format, should be \"currency amount\"");
        }

        if (values[0].length() != 3) {
            throw new IllegalArgumentException("Currency should be a 3 letter code, e.g.: USD");
        }

        try {
            new BigDecimal(values[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount(second parameter) is not a valid number");
        }
    }

}
