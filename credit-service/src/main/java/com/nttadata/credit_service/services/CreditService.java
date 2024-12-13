package com.nttadata.credit_service.services;

import com.nttadata.credit_service.dto.CreditDto;
import com.nttadata.credit_service.entity.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    Mono<Credit> save(CreditDto creditDto);
    Mono<Credit> findById(String Id);
    Flux<Credit> findAll();
    Mono<Credit> udpate(CreditDto creditDto);
    Mono<Void> delete(String Id);

}
