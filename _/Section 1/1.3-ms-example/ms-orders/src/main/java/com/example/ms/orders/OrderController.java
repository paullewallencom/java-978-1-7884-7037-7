package com.example.ms.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final static String PAYMENTS_URL = "http://localhost:8082/v1";

    private final OrderRepo orderRepo;

    private final RestTemplate restTemplate;

    @Autowired
    public OrderController(OrderRepo orderRepo, RestTemplateBuilder restTemplateBuilder) {
        this.orderRepo = orderRepo;
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity getOrders() {

        List<Order> allOrders = orderRepo.findAllOrders();

        for (Order o : allOrders) {

            try {
                Transaction transaction = this.restTemplate
                        .getForObject(PAYMENTS_URL + "/transactions/{id}", Transaction.class, o.getTransactionId());

                o.setCardType(transaction.getCardDetails().getType());
                o.setCardLast4(transaction.getCardDetails().getLast4());
            } catch (RestClientException ignored) {}
        }

        return ResponseEntity.ok().body(allOrders);
    }
}
