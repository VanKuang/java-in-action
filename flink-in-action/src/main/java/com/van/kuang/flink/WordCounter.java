package com.van.kuang.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCounter {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        final DataStreamSource<String> source = environment.socketTextStream("localhost", 9999, "\n");

        SingleOutputStreamOperator<WordCount> sum = source.flatMap(
                new FlatMapFunction<String, WordCount>() {
                    @Override
                    public void flatMap(String value, Collector<WordCount> collector) throws Exception {
                        String[] words = value.split("\\s");
                        for (String word : words) {
                            collector.collect(new WordCount(word, 1));
                        }
                    }
                })
                .keyBy("word")
                .timeWindow(Time.seconds(2), Time.seconds(1))
                .sum("count");

        sum.print().setParallelism(1);

        environment.execute("count");
    }

    public static class WordCount {

        public String word;
        public long count;

        public WordCount() {
        }

        public WordCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
