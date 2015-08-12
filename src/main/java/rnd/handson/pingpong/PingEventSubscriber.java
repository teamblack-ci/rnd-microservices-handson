package rnd.handson.pingpong;

import rnd.shared.event.AbstractEventSubscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("!test")
public class PingEventSubscriber extends AbstractEventSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingEventSubscriber.class);

    @Autowired
    public PingEventSubscriber(ObjectMapper objectMapper) {
        super(objectMapper, "ping");
    }

    @Override
    protected void handleOwnType(Map<String, Object> event) {
        final Object payload = getPayload(event);
        LOGGER.info("Consumed PING event with payload '{}' of class '{}'", payload, payload.getClass());
    }

    @Override
    protected void handleForeignType(Map<String, Object> event) {
        LOGGER.info("Ignored event of type '{}'", getType(event));
    }
}
