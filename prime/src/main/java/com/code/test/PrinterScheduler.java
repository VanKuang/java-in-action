package com.code.test;

import com.code.test.repository.PaymentRepository;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PrinterScheduler {

    private final PaymentRepository paymentRepository;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public PrinterScheduler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void start() {
        executorService.scheduleAtFixedRate(() -> {
            try {
                final Set<Payment> payments = paymentRepository.getAll();
                final StringBuilder content = new StringBuilder("*****payments start*****").append(System.lineSeparator());
                payments.forEach(p -> content.append(p.getCcy()).append(" ").append(p.getAmount().toPlainString()).append(System.lineSeparator()));
                content.append("*****payments end*****");
                System.out.println(content.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    public void stop() {
        executorService.shutdownNow();
    }
}
