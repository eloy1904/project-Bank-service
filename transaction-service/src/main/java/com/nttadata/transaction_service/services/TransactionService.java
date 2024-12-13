package com.nttadata.transaction_service.services;

import com.nttadata.transaction_service.dto.TransactionDto;
import com.nttadata.transaction_service.entity.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> save(TransactionDto transactionDto);
    Mono<Transaction> validateTransaction(TransactionDto transactionDto);
    Mono<Transaction> findById(String Id);
    Flux<Transaction> findAll();
    Mono<Transaction> udpate(TransactionDto creditDto);
    Mono<Void> delete(String Id);
}
