package org.sample;

import ch.qos.logback.classic.spi.ILoggingEvent;
import net.logstash.logback.LogstashFormatter;
import net.logstash.logback.composite.CompositeJsonFormatter;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;
import net.logstash.logback.encoder.LogstashEncoder;

/**
 * Overwrites basic {@link net.logstash.logback.encoder.LogstashEncoder LogstashEncoder}
 * to replace one of pocket {@link net.logstash.logback.composite.JsonProvider JsonProviders} -
 * {@link net.logstash.logback.composite.loggingevent.MessageJsonProvider} -
 * with custom one {@link TruncMessageJsonProvider}.
 */
public class TruncMessageLogstashEncoder extends LogstashEncoder {
    private static final int MAX_MESSAGE_LEN = 2 * 1024 * 1024; // 2MB

    @Override
    protected CompositeJsonFormatter<ILoggingEvent> createFormatter() {
        LogstashFormatter formatter = (LogstashFormatter) super.createFormatter();
        formatter.getProviders().getProviders().stream()
                .filter(provider -> provider instanceof MessageJsonProvider)
                .findFirst()
                .ifPresent(formatter.getProviders()::removeProvider);
        formatter.getProviders().addMessage(new TruncMessageJsonProvider(MAX_MESSAGE_LEN));
        return formatter;
    }
}
