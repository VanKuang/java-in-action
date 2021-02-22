package com.code.test.handler;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class ExitHandlerTest {

    @Test
    public void testProcess() {
        final AtomicBoolean isExitBeCalled = new AtomicBoolean(false);
        final Runnable exitHandler = () -> isExitBeCalled.compareAndSet(false, true);
        final ExitHandler handler = new ExitHandler(exitHandler);
        handler.process("quit");

        assertTrue(isExitBeCalled.get());
    }

}