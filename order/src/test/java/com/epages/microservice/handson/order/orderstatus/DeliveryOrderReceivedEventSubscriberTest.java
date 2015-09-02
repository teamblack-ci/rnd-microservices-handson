package com.epages.microservice.handson.order.orderstatus;

import com.epages.microservice.handson.order.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OrderApplication.class)
@WebAppConfiguration
@ActiveProfiles("DeliveryOrderReceivedEventSubscriberTest")
public class DeliveryOrderReceivedEventSubscriberTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryOrderReceivedEventSubscriber deliveryOrderReceivedEventSubscriber;

    private String event = "{\n" +
            "  \"type\" : \"DeliveryOrderReceived\",\n" +
            "  \"timestamp\" : \"2015-09-01T21:43:48.391\",\n" +
            "  \"payload\" : {\n" +
            "      \"orderLink\" : \"http://192.168.99.100:8081/orders/1\",\n" +
            "      \"estimatedTimeOfDelivery\" : \"2015-09-12T07:43:48.391\"\n" +
            "  }\n" +
            "}";

    private Order order;

    @Configuration
    @Profile("DeliveryOrderReceivedEventSubscriberTest")
    public static class MockConfiguration {
        @Bean
        public OrderService orderService() {
            return mock(OrderServiceImpl.class);
        }
    }

    @Test
    public void should_set_status_and_date_on_order() {
        givenOrder();

        whenEventConsumed();

        then(order.getStatus()).isEqualTo(OrderStatus.BAKING);
        then(order.getEstimatedTimeOfDelivery()).isNotNull();
        then(order.getEstimatedTimeOfDelivery().getDayOfMonth()).isEqualTo(12);
        then(order.getEstimatedTimeOfDelivery().getHour()).isEqualTo(7);
    }

    private void whenEventConsumed() {
        deliveryOrderReceivedEventSubscriber.consume(event);
    }

    private void givenOrder() {
        order = new Order();
        order.setId(1l);
        order.setStatus(OrderStatus.NEW);

        when(orderService.getOrder(1l)).thenReturn(Optional.of(order));
    }
}
