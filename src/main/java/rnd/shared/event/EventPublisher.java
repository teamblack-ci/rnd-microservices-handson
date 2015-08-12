package rnd.shared.event;

import rnd.shared.json.JsonMapTypeReference;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

public class EventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventPublisher.class);

    public static final String EVENT_TYPE = "type";

    public static final String EVENT_TIMESTAMP = "timestamp";

    public static final String EVENT_PAYLOAD = "payload";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String type, String jsonPayload) {
        final String event = createEvent(type, jsonPayload);
        LOGGER.info("Publishing event '{}'", event);
        rabbitTemplate.convertAndSend(event);
    }

    protected String createEvent(String type, String jsonPayload) {
        try {
            return objectMapper.writeValueAsString(ImmutableMap.of( //
                    EVENT_TYPE, type, //
                    EVENT_TIMESTAMP, LocalDateTime.now().toString(), //
                    EVENT_PAYLOAD, objectMapper.readValue(jsonPayload, new JsonMapTypeReference()) //
            ));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not serialize event from payload '%s'", jsonPayload), e);
        }
    }
}
