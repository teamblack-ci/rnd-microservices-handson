package com.epages.microservice.handson.order;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OrderApplication.class)
@WebAppConfiguration
public class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    private ResultActions ordersResultAction;

    private String ordersUri;

    private String jsonInput;

    private String pizzaSampleResponse = "{\n" +
            "  \"name\": \"Pizza Salami\",\n" +
            "  \"description\": \"The classic - Pizza Salami\",\n" +
            "  \"imageUrl\": \"http://www.sardegna-rustica.de/images/pizza.jpg\",\n" +
            "  \"price\": \"EUR 8.90\"}";

    private Order order;

    @Configuration
    public static class OrderEventPublisherMockConfiguration {

        @Bean
        public OrderEventPublisher orderEventPublisher() {
            return mock(OrderEventPublisher.class);
        }
    }

    @Before
    public void setupContext(){
        mockMvc = webAppContextSetup(context)
                .build();

        //mock the rest call made b< OrderServiceImpl to PizzaServiceClient
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(
                requestTo("http://localhost/catalog/1")).
                andRespond(withSuccess(pizzaSampleResponse, MediaType.APPLICATION_JSON));

        ordersUri = linkTo(methodOn(OrderController.class).getAll(null, null)).toUri().toString();
    }

    @Test
    public void should_create_order() throws Exception {
        givenInputData();

        whenOrderCreated();

        ordersResultAction
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(header().string(HttpHeaders.LOCATION, startsWith(ordersUri)))
        ;

        verify(orderEventPublisher).sendOrderCreatedEvent(order);
    }

    @Test
    public void should_get_order() throws Exception {
        givenExistingOrder();

        whenOrderRetrieved();

        ordersResultAction
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.status", is(order.getStatus().name())))
                .andExpect(jsonPath("$.totalPrice", notNullValue()))
                .andExpect(jsonPath("$.orderItems", hasSize(order.getItems().size())))
                .andExpect(jsonPath("$.deliveryAddress.firstname", is(order.getDeliveryAddress().getFirstname())))
                .andExpect(jsonPath("$._links.self.href",
                        is(entityLinks.linkForSingleResource(Order.class, order.getId()).toUri().toString())))
        ;
    }

    private void whenOrderRetrieved() throws Exception {
        URI orderUri = entityLinks.linkForSingleResource(Order.class, order.getId()).toUri();

        ordersResultAction = mockMvc.perform(get(orderUri)
                .accept(MediaTypes.HAL_JSON));
    }

    private void givenExistingOrder() throws URISyntaxException {
        Order orderTmp = new Order();
        orderTmp.setComment("some comment");
        Address address = new Address();
        address.setCity("Hamburg");
        address.setFirstname("Mathias");
        address.setLastname("Dpunkt");
        address.setPostalCode("22222");
        address.setStreet("Pilatuspool 2");
        address.setTelephone("+4908154711");
        orderTmp.setDeliveryAddress(address);

        LineItem lineItem = new LineItem();
        lineItem.setAmount(2);
        lineItem.setPizza(new URI("http://localhost/catalog/1"));

        orderTmp.addItem(lineItem);

        order = orderService.create(orderTmp);
    }

    private void whenOrderCreated() throws Exception {
        ordersResultAction = mockMvc.perform(post(ordersUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput));

        order = orderService.getAll(null).iterator().next();
    }

    private void givenInputData() throws JsonProcessingException {
        ImmutableMap<Object, Object> address = ImmutableMap.builder().putAll(ImmutableMap.of(
                "firstname", "Mathias",
                "lastname", "Dpunkt",
                "street", "Somestreet 1",
                "city", "Hamburg",
                "telephone", "+49404321343"
        )).put("postalCode", "22305").build();

        jsonInput = objectMapper.writeValueAsString(ImmutableMap.of(
                "comment", "Some comment",
                "deliveryAddress", address,
                "orderItems", ImmutableList.of(ImmutableMap.of(
                                "amount", 1,
                                "pizza", "http://localhost/catalog/1"
                        )
                )
        ));
    }
}
