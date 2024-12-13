package com.nttadata.transaction_service.client;

import com.nttadata.transaction_service.dto.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountClient {
    @Autowired
    private final WebClient.Builder webClientBuilder;

    public AccountClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<AccountResponse> getAccountById(String accountId) {
        return webClientBuilder.build()
                .get()
                .uri("lb://account-service/api/v1/account/{id}", accountId)
                .retrieve()
                .bodyToMono(AccountResponse.class);
    }
}
