package com.nttadata.credit_service.controller;


import com.nttadata.credit_service.dto.CreditDto;
import com.nttadata.credit_service.entity.Credit;
import com.nttadata.credit_service.services.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/credit")
@RequiredArgsConstructor
@Slf4j
public class CreditController {
    private final CreditService creditService;

    @PostMapping("/save")
    public ResponseEntity<Mono<Credit>> save(@RequestBody CreditDto creditDto)throws ExecutionException, InterruptedException{
        Mono<Credit> creditSave = creditService.save(creditDto);
        return new ResponseEntity<>(creditSave, HttpStatus.OK);
    }

    @GetMapping("/{creditId}")
    public ResponseEntity<Mono<Credit>> findById(@PathVariable("creditId") String id) {
        Mono<Credit> findId = creditService.findById(id);
        return new ResponseEntity<Mono<Credit>>(findId, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Flux<Credit> findAll() {
        Flux<Credit> creditAll = creditService.findAll();
        return creditAll;
    }

    @PutMapping("/update")
    public Mono<Credit> update(@RequestBody CreditDto creditDto) {
        return creditService.udpate(creditDto);
    }

    @DeleteMapping("/{creditId}")
    public void delete(@PathVariable("creditId") String id) {
        creditService.delete(id).subscribe();
    }
}
