package com.van.kuang.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class StreamingWordCounter {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> source = env.socketTextStream("localhost", 9999, "\n");

        DataStream<WordCount> sum = source.flatMap(
                new FlatMapFunction<String, WordCount>() {
                    @Override
                    public void flatMap(String value, Collector<WordCount> collector) throws Exception {
                        String[] values = value.split("\\s");
                        for (String v : values) {
                            collector.collect(new WordCount(v, 1));
                        }
                    }
                })
                .keyBy("word")
                .timeWindow(Time.seconds(2), Time.seconds(1))
                .sum("count");

        sum.print().setParallelism(1);

        env.execute("counter");
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
