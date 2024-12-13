package com.nttadata.transaction_service.services.implement;

import com.nttadata.transaction_service.dto.TransactionDto;
import com.nttadata.transaction_service.entity.Transaction;
import com.nttadata.transaction_service.client.*;
import com.nttadata.transaction_service.repository.TransactionRepository;
import com.nttadata.transaction_service.services.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {
    @NonNull
    private final TransactionRepository transactionRepository;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private KafkaSender<String, TransactionDto> kafkaSender;


    //En esta parte se implementa logica de una transaccion

    public Mono<Transaction> validateTransaction(TransactionDto transactionDto){
        return  accountClient.getAccountById(transactionDto.getAccountId())
                .flatMap(account ->{
                    if ("withdrawal".equals(transactionDto.getType()) && account.getBalance() < transactionDto.getAmount()){
                        return Mono.error(new IllegalArgumentException("Saldo insuficiente."));
                    }
                    //return Mono.just(transactionDto);
                    /*
                    return save(transactionDto)
                            .flatMap(savedTransaction->{
                                String message = "Transaction successful:" +transactionDto.getAmount();
                                return customerClient.notifyCustomer(transactionDto.getCustomerId(), message)
                                        .thenReturn(creditDtoToEntity(transactionDto));
                                    });*/
                    return save(transactionDto)
                            .flatMap(savedTransaction ->{
                                //String message = "Transaction successful: " + savedTransaction.getAmount();
                                return sendToKafka("transactions-topic", transactionDto)
                                        .thenReturn(savedTransaction);
                            });


                });
    }

    @Override
    public Mono<Transaction> save(TransactionDto transactionDto) {
        if(Objects.nonNull(transactionDto)){
            return transactionRepository.save(creditDtoToEntity(transactionDto));
        }else{
            return null;
        }
    }

    @Override
    public Mono<Transaction> findById(String Id) {
        return transactionRepository.findById(Id);
    }

    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    @Override
    public Mono<Transaction> udpate(TransactionDto transactionDto) {
        return this.transactionRepository.findById(transactionDto.getTransactionId())
                .map(credit -> creditDtoToEntity(transactionDto))
                .flatMap(this.transactionRepository::save);
    }

    @Override
    public Mono<Void> delete(String Id) {
        return transactionRepository.deleteById(Id);
    }

    public static Transaction creditDtoToEntity(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionDto, transaction);
        return transaction;
    }

    public static TransactionDto mapToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setAccountId(transaction.getAccountId());
        dto.setCustomerId(transaction.getCustomerId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        return dto;
    }
    public Mono<Void> sendToKafka(String topic, TransactionDto transaction) {
        return kafkaSender.send(
                Mono.just(SenderRecord.create(new ProducerRecord<>(topic, transaction.getTransactionId(), transaction), null))
        ).then();
    }

}
