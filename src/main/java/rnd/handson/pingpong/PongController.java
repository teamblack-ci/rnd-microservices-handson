package rnd.handson.pingpong;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import rnd.shared.event.EventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pong", produces = APPLICATION_JSON_VALUE)
public class PongController {

    @Autowired
    private EventPublisher eventPublisher;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> pong(@RequestBody String payload) {
        eventPublisher.publish("pong", payload);
        return ResponseEntity.accepted().build();
    }
}
