package org.sample;

import ch.qos.logback.classic.spi.LoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;


public class TruncMessageJsonProviderBenchmark {
    private static final int MAX_LEN = 2 * 1024 * 1024;      // 2097152
    private static final String SMALL_LEN = "500";
    private static final String MAX_LEN_BUT_ONE = "2097151"; // 2 * 1024 * 1024 - 1
    private static final String MAX_LEN_TWICE = "4194304";   // 4 * 1024 * 1024
    private static final String MAX_LEN_AND_ONE = "2097153"; // 2 * 1024 * 1024 + 1


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3)
    public void writeWithTruncProvider(BenchmarkState state) throws IOException {
        state.truncProvider.writeTo(state.jsonGenerator, state.event);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 3)
    public void writeWithDefaultProvider(BenchmarkState state) throws IOException {
        state.defaultProvider.writeTo(state.jsonGenerator, state.event);
    }

    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({SMALL_LEN, MAX_LEN_BUT_ONE, MAX_LEN_AND_ONE, MAX_LEN_TWICE})
        public int messageLen;

        public JsonGenerator jsonGenerator;
        public TruncMessageJsonProvider truncProvider;
        public MessageJsonProvider defaultProvider;
        public LoggingEvent event;

        @Setup(Level.Trial)
        public void setup() {
            jsonGenerator = new DummyJsonGenerator();
            truncProvider = new TruncMessageJsonProvider(MAX_LEN);
            defaultProvider = new MessageJsonProvider();

            event = new LoggingEvent();
            event.setMessage(StringUtils.repeat('A', messageLen));
        }
    }
}
