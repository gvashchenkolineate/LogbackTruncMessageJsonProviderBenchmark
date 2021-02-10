package org.sample;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.logstash.logback.composite.JsonWritingUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Extends {@link net.logstash.logback.composite.loggingevent.MessageJsonProvider}
 * and puts truncated messages to JSON output.
 * If messages truncates adds '...' to the end.
 * <br/>
 * Maximum message length before truncating is hardcoded here as MAX_MESSAGE_LEN.
 * <br/>
 * Purpose: avoid extremely long messages causing logs shipping service failures.
 */
@Getter
@Setter
@AllArgsConstructor
public class TruncMessageJsonProvider extends net.logstash.logback.composite.loggingevent.MessageJsonProvider {
    private final int maxLength;

    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        JsonWritingUtils.writeStringField(generator, getFieldName(),
                StringUtils.abbreviate(event.getFormattedMessage(), maxLength));
    }
}
