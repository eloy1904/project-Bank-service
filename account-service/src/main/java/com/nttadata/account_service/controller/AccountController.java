package com.nttadata.account_service.controller;

import com.nttadata.account_service.dto.AccountDto;
import com.nttadata.account_service.entity.Account;
import com.nttadata.account_service.entity.response.AccountResponse;
import com.nttadata.account_service.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/save")
    public ResponseEntity<Mono<Account>> save(@RequestBody AccountDto accountDto)throws ExecutionException, InterruptedException{
        Mono<Account> creditSave = accountService.save(accountDto);
        return new ResponseEntity<>(creditSave, HttpStatus.OK);
    }
/*
    @GetMapping("/{accountId}")
    public ResponseEntity<Mono<Account>> findById(@PathVariable("accountId") String id) {
        Mono<Account> findId = accountService.findById(id);
        return new ResponseEntity<Mono<Account>>(findId, HttpStatus.OK);
    }*/

    @GetMapping("/{id}")
    public Mono<AccountResponse> getAccountById(@PathVariable String id) {
        return accountService.findById(id).map(account -> {
            AccountResponse response = new AccountResponse();
            response.setId(account.getId());
            response.setAccountNumber(account.getAccountNumber());
            response.setBalance(account.getBalance());
            response.setType(account.getType());
            return response;
        });
    }

    @GetMapping("/all")
    public Flux<Account> findAll() {
        Flux<Account> creditAll = accountService.findAll();
        return creditAll;
    }

    @PutMapping("/update")
    public Mono<Account> update(@RequestBody AccountDto accountDto) {
        return accountService.update(accountDto);
    }

    @DeleteMapping("/{accountId}")
    public void delete(@PathVariable("accountId") String id) {
        accountService.delete(id).subscribe();
    }
}
