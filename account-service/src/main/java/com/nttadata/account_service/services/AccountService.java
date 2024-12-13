package com.nttadata.account_service.services;

import com.nttadata.account_service.dto.AccountDto;
import com.nttadata.account_service.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> save(AccountDto transactionDto);
    Mono<Account> findById(String Id);
    Flux<Account> findAll();
    Mono<Account> update(AccountDto transactionDto);
    Mono<Void> delete(String Id);
}
