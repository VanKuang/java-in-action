package com.code.test;

import com.code.test.handler.InputHandler;

import java.util.function.Consumer;

public class Dispatcher {

    private final InputHandler exitHandler;
    private final InputHandler defaultInputHandler;
    private final Consumer<Exception> exceptionHandler;

    public Dispatcher(final InputHandler exitHandler,
                      final InputHandler defaultInputHandler,
                      final Consumer<Exception> exceptionHandler) {
        this.exitHandler = exitHandler;
        this.defaultInputHandler = defaultInputHandler;
        this.exceptionHandler = exceptionHandler;
    }

    public void accept(final String input) {
        final InputHandler inputHandler;
        switch (input) {
            case "quit":
                inputHandler = exitHandler;
                break;
            default:
                inputHandler = defaultInputHandler;
                break;
        }

        try {
            inputHandler.process(input);
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

}
