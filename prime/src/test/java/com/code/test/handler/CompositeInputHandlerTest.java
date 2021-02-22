package com.code.test.handler;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class CompositeInputHandlerTest {

    @Test(expected = RuntimeException.class)
    public void testFailToProcess() {
        final InputHandler handler1 = mock(InputHandler.class);
        final InputHandler handler2 = mock(InputHandler.class);

        final CompositeInputHandler inputHandler = new CompositeInputHandler(handler1, handler2);
        doThrow(new RuntimeException("test")).when(handler1).process(anyString());
        inputHandler.process("USD 100");

        verify(handler1, times(1)).process("USD 100");
        verify(handler2, times(0)).process("USD 100");
    }

    @Test
    public void testProcessSuccessfully() {
        final InputHandler handler1 = mock(InputHandler.class);
        final InputHandler handler2 = mock(InputHandler.class);

        final CompositeInputHandler inputHandler = new CompositeInputHandler(handler1, handler2);
        inputHandler.process("USD 100");
        verify(handler1, times(1)).process("USD 100");
        verify(handler2, times(1)).process("USD 100");
    }

}