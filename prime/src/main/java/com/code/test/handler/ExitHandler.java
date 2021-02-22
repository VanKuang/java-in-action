package com.code.test.handler;

public class ExitHandler implements InputHandler {

    private final Runnable exitHandler;

    public ExitHandler() {
        this(() -> System.exit(1));
    }

    public ExitHandler(Runnable exitHandler) {
        this.exitHandler = exitHandler;
    }

    @Override
    public void process(String input) {
        System.out.println("Received \"quit\" command, will exit now");
        exitHandler.run();
    }

}
