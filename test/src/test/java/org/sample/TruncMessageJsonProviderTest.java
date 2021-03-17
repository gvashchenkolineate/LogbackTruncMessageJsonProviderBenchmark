package org.sample;

import ch.qos.logback.classic.spi.LoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TruncMessageJsonProviderTest {
    private static final int MAX_LEN = 1 * 1024 * 1024;
    private static final int MAX_LEN_BUT_ONE = 1 * 1024 * 1024 - 1;
    private static final int MAX_LEN_AND_MORE = 10 * 1024 * 1024;

    private JsonGenerator jsonGenerator;
    private TruncMessageJsonProvider provider;

    @BeforeEach
    void setUp() throws IOException {
        jsonGenerator = mock(JsonGenerator.class);
        doNothing().when(jsonGenerator).writeStringField(anyString(), anyString());
    }

    @Test
    void givenSmallMessage_whenWritingJson_thenLeftMessageAsIs() throws IOException {
        provider = new TruncMessageJsonProvider(MAX_LEN);

        String smallMessage = StringUtils.repeat('A', MAX_LEN_BUT_ONE);
        LoggingEvent event = new LoggingEvent();
        event.setMessage(smallMessage);

        provider.writeTo(jsonGenerator, event);

        verify(jsonGenerator).writeStringField(anyString(), argThat(message -> message.equals(smallMessage)));
    }

    @Test
    void givenExtremelyLargeMessage_whenWritingJson_thenTruncateMessage() throws IOException {
        provider = new TruncMessageJsonProvider(MAX_LEN);

        String largeMessage = StringUtils.repeat('A', MAX_LEN_AND_MORE);
        LoggingEvent event = new LoggingEvent();
        event.setMessage(largeMessage);

        String truncatedMessage = StringUtils.abbreviate(largeMessage, MAX_LEN);

        provider.writeTo(jsonGenerator, event);

        verify(jsonGenerator).writeStringField(anyString(), argThat(message ->
                message.length() == MAX_LEN
                        && message.equals(truncatedMessage)));
    }
}
