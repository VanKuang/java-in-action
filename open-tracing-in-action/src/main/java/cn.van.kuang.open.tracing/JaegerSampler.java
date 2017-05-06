package cn.van.kuang.open.tracing;

import com.uber.jaeger.Tracer;
import com.uber.jaeger.metrics.*;
import com.uber.jaeger.reporters.CompositeReporter;
import com.uber.jaeger.reporters.LoggingReporter;
import com.uber.jaeger.reporters.RemoteReporter;
import com.uber.jaeger.reporters.Reporter;
import com.uber.jaeger.samplers.ConstSampler;
import com.uber.jaeger.samplers.Sampler;
import com.uber.jaeger.senders.Sender;
import com.uber.jaeger.senders.UDPSender;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import io.opentracing.propagation.TextMapInjectAdapter;

public class JaegerSampler {

    public void report() {
        StatsReporter statsReporter = new InMemoryStatsReporter();
        StatsFactory statsFactory = new StatsFactoryImpl(statsReporter);
        Metrics metrics = new Metrics(statsFactory);

        Sender udpSender = new UDPSender("localhost", 5775, 1024);
        Reporter loggingReporter = new LoggingReporter();
        Reporter remoteReporter = new RemoteReporter(udpSender, 10, 1000, metrics);

        Reporter compositeReporter = new CompositeReporter(loggingReporter, remoteReporter);
        Sampler sampler = new ConstSampler(true);

        Deal deal = new Deal();

        tg(compositeReporter, sampler, deal);

        tp(compositeReporter, sampler, deal);
    }

    private void tg(Reporter compositeReporter, Sampler sampler, Deal deal) {
        Tracer tgTracer = new Tracer.Builder("TG", compositeReporter, sampler).build();

        Span tgRootTracer = tgTracer.buildSpan("tg_process").withTag("id", deal.getId()).start();

        Span enrichment = tgTracer.buildSpan("enrichment").asChildOf(tgRootTracer).start();
        sleep(2L);
        enrichment.finish();

        Span mapping = tgTracer.buildSpan("mapping").asChildOf(tgRootTracer).start();
        sleep(1L);
        mapping.finish();

        Span transform = tgTracer.buildSpan("transform").asChildOf(tgRootTracer).start();
        sleep(2L);
        transform.finish();

        Span publish = tgTracer.buildSpan("publish").asChildOf(tgRootTracer).start();
        sleep(1L);
        publish.finish();

        tgTracer.inject(tgRootTracer.context(), Format.Builtin.TEXT_MAP, new TextMapInjectAdapter(deal.getTrace()));

        tgRootTracer.finish();
    }

    private void tp(Reporter compositeReporter, Sampler sampler, Deal deal) {
        Tracer tpTracer = new Tracer.Builder("TP", compositeReporter, sampler).build();

        SpanContext spanContext = tpTracer.extract(Format.Builtin.TEXT_MAP, new TextMapExtractAdapter(deal.getTrace()));

        Span tpRootTracer = tpTracer.buildSpan("tp_process").asChildOf(spanContext).withTag("id", deal.getId()).start();

        Span transform = tpTracer.buildSpan("transform").asChildOf(tpRootTracer).start();
        sleep(2L);
        transform.finish();

        Span publish = tpTracer.buildSpan("publish").asChildOf(tpRootTracer).start();
        sleep(2L);
        publish.finish();

        tpRootTracer.finish();
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time * 2000L);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args) {
        JaegerSampler jaegerSampler = new JaegerSampler();

        jaegerSampler.report();

        while (Thread.currentThread().isAlive()) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }

}
