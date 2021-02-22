package com.code.test.handler;

import com.code.test.Payment;
import com.code.test.repository.PaymentRepository;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class PaymentHandlerTest {

    @Test
    public void testProcess() {
        final PaymentRepository paymentRepository = mock(PaymentRepository.class);
        final PaymentHandler paymentHandler = new PaymentHandler(paymentRepository);
        paymentHandler.process("USD 100");

        verify(paymentRepository, times(1)).save(refEq(new Payment("USD", BigDecimal.valueOf(100))));
    }

}