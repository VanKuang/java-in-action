package com.code.test;

import com.code.test.handler.InputHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DispatcherTest {

    private InputHandler exitHandler;
    private InputHandler defaultHandler;

    @Before
    public void setUp() {
        exitHandler = mock(InputHandler.class);
        defaultHandler = mock(InputHandler.class);
    }

    @Test
    public void testFailToProcess() {
        doThrow(new RuntimeException("test")).when(defaultHandler).process(anyString());

        final AtomicBoolean hasException = new AtomicBoolean(false);
        final Consumer<Exception> exceptionHandler = e -> {
            hasException.compareAndSet(false, true);
        };
        final Dispatcher dispatcher = new Dispatcher(exitHandler, defaultHandler, exceptionHandler);
        dispatcher.accept("USD 100");

        verify(exitHandler, times(0)).process(anyString());
        verify(defaultHandler, times(1)).process(anyString());
        assertTrue(hasException.get());
    }

    @Test
    public void testInputQuit() {
        final Dispatcher dispatcher = new Dispatcher(exitHandler, defaultHandler, e -> {
        });
        dispatcher.accept("quit");
        verify(exitHandler, times(1)).process("quit");
        verify(defaultHandler, times(0)).process(anyString());
    }

    @Test
    public void testFallToDefaultHandler() {
        final Dispatcher dispatcher = new Dispatcher(exitHandler, defaultHandler, e -> {
        });
        dispatcher.accept("USD 100");
        verify(exitHandler, times(0)).process(anyString());
        verify(defaultHandler, times(1)).process("USD 100");
    }
}