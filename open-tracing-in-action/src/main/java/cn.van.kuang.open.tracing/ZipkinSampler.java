package cn.van.kuang.open.tracing;

import brave.Tracer;
import brave.Tracing;
import brave.sampler.Sampler;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.okhttp3.OkHttpSender;

public class ZipkinSampler {

    public static void main(String[] args) {
        report();
        report();
    }

    private static void report() {
        OkHttpSender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v1/spans");

        AsyncReporter<Span> reporter = AsyncReporter.builder(sender).build();

        Tracing tracing = Tracing
                .newBuilder()
                .localServiceName("TG")
                .reporter(reporter)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build();

        Tracer tracer = tracing.tracer();

        brave.Span rootSpan = tracer
                .newTrace()
                .name("TG_PROCESS")
                .tag("ID", "ABC" + System.currentTimeMillis());

        try {
            rootSpan.start();
            System.out.println(System.currentTimeMillis());

            brave.Span enrichment = tracer.newChild(rootSpan.context()).name("enrichment");
            enrichment.start();
            try {
                sleepSilently(20L);
            } finally {
                enrichment.finish();
            }

            System.out.println(System.currentTimeMillis());


            brave.Span mapping = tracer.newChild(rootSpan.context()).name("mapping");
            mapping.start();
            try {
                sleepSilently(10L);
            } finally {
                mapping.finish();
            }

            System.out.println(System.currentTimeMillis());


            brave.Span transform = tracer.newChild(rootSpan.context()).name("transform");
            transform.start();
            try {
                sleepSilently(30L);
            } finally {
                transform.finish();
            }
            System.out.println(System.currentTimeMillis());

        } finally {
            rootSpan.finish();
            System.out.println(System.currentTimeMillis());

            reporter.flush();

            tracing.close();
            reporter.close();
        }

        System.out.println("DONE");
    }

    private static void sleepSilently(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException ignored) {

        }
    }


}
