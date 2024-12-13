package com.nttadata.transaction_service.controller;

import com.nttadata.transaction_service.dto.TransactionDto;
import com.nttadata.transaction_service.entity.Transaction;
import com.nttadata.transaction_service.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<Mono<Transaction>> save(@RequestBody TransactionDto transactionDto)throws ExecutionException, InterruptedException{
        Mono<Transaction> creditSave = transactionService.validateTransaction(transactionDto);
        return new ResponseEntity<>(creditSave, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Mono<Transaction>> findById(@PathVariable("transactionId") String id) {
        Mono<Transaction> findId = transactionService.findById(id);
        return new ResponseEntity<Mono<Transaction>>(findId, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Flux<Transaction> findAll() {
        Flux<Transaction> transactionAll = transactionService.findAll();
        return transactionAll;
    }

    @PutMapping("/update")
    public Mono<Transaction> update(@RequestBody TransactionDto transactionDto) {
        return transactionService.udpate(transactionDto);
    }

    @DeleteMapping("/{transactionId}")
    public void delete(@PathVariable("transactionId") String id) {
        transactionService.delete(id).subscribe();
    }
}
