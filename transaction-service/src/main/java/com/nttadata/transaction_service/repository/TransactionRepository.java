package com.nttadata.transaction_service.repository;

import com.nttadata.transaction_service.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    //Flux<Transaction> findByProductId(String productId);
}
