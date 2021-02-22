package com.code.test.repository;

import com.code.test.Payment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CacheablePaymentRepository implements PaymentRepository {

    private final Map<String, Payment> payments = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    private Path file;
    private BufferedWriter bufferedWriter;

    public void setFile(final Path file) {
        this.file = file;
    }

    public void init() {
        if (file != null) {
            init(file);
        }
    }

    @Override
    public void save(final Payment payment) {
        // TODO use queue to improve performance
        lock.lock();
        try {
            bufferedWriter.write(payment.getCcy() + " " + payment.getAmount().toPlainString() + System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Fail to save payment, reason:");
            e.printStackTrace();
            throw new UncheckedIOException(e);
        } finally {
            lock.unlock();
        }
        payments.compute(payment.getCcy(), (ccy, p) -> new Payment(ccy, p == null ? payment.getAmount() : p.getAmount().add(payment.getAmount())));
        System.out.println("Saved " + payment.getCcy());
    }

    @Override
    public Payment get(final String ccy) {
        return payments.get(ccy);
    }

    @Override
    public Set<Payment> getAll() {
        return new HashSet<>(payments.values());
    }

    private void init(final Path file) {
        try {
            if (file.toFile().exists()) {
                // TODO use buffer reader to improve performance if the size is big
                final List<String> lines = Files.readAllLines(file);
                lines.forEach(line -> {
                    final String[] values = line.split(" ");
                    final Payment payment = new Payment(values[0], new BigDecimal(values[1]));
                    payments.compute(payment.getCcy(), (ccy, p) -> new Payment(ccy, p == null ? payment.getAmount() : p.getAmount().add(payment.getAmount())));
                });
                System.out.println(file.toFile().getName() + " loaded, size=" + payments.size());
            } else {
                // initial the file with empty content
                Files.write(file, new byte[0]);
                System.out.println(file.toFile().getName() + " created");
            }
            bufferedWriter = Files.newBufferedWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UncheckedIOException(e);
        }
    }
}
