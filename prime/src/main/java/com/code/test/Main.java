package com.code.test;

import com.code.test.handler.CompositeInputHandler;
import com.code.test.handler.ExitHandler;
import com.code.test.handler.InputValidator;
import com.code.test.handler.PaymentHandler;
import com.code.test.repository.CacheablePaymentRepository;

import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final CacheablePaymentRepository paymentRepository = new CacheablePaymentRepository();
        final PrinterScheduler printerScheduler = new PrinterScheduler(paymentRepository);

        Runtime.getRuntime().addShutdownHook(new Thread(printerScheduler::stop));

        System.out.println("please input the file name:");

        Dispatcher dispatcher = null;
        final Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            final String input = sc.nextLine();
            if (dispatcher == null) {
                paymentRepository.setFile(Paths.get(input));
                paymentRepository.init();

                dispatcher = new Dispatcher(
                        new ExitHandler(),
                        new CompositeInputHandler(
                                new InputValidator(),
                                new PaymentHandler(paymentRepository)),
                        Throwable::printStackTrace);

                printerScheduler.start();

                System.out.println("can start input payment...");
            } else {
                dispatcher.accept(input);
            }
        }
    }

}
