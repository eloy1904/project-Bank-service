package com.nttadata.transaction_service.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerClient {
    private final WebClient.Builder webClientBuilder;

    public CustomerClient(WebClient.Builder webCLientBuilder){
        this.webClientBuilder = webCLientBuilder;
    }

    public Mono<Void> notifyCustomer(String customerId, String message) {
        return webClientBuilder.build()
                .post()
                .uri("lb://customer-service/api/v1/customer/notify")
                .bodyValue(new CustomerNotification(customerId, message))
                .retrieve()
                .bodyToMono(Void.class);
    }

}
