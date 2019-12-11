package com.van.kuang.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class BatchWordCounter {

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        final DataSource<String> source = env.readTextFile("/Users/VanKuang/Development");

        DataSet<Tuple2<String, Long>> sum = source.flatMap(
                new FlatMapFunction<String, Tuple2<String, Long>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Long>> collector) throws Exception {
                        String[] values = value.split("\\s");
                        for (String v : values) {
                            collector.collect(new Tuple2<>(v, 1L));
                        }
                    }
                })
                .groupBy(0)
                .sum(1);

        sum.print();

        env.execute("batch count");
    }

}
