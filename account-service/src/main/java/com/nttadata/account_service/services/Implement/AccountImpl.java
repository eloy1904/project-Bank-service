package com.nttadata.account_service.services.Implement;

import com.nttadata.account_service.dto.AccountDto;
import com.nttadata.account_service.entity.Account;
import com.nttadata.account_service.repository.AccountRepository;
import com.nttadata.account_service.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Mono<Account> save(AccountDto transactionDto) {
        if(Objects.nonNull(transactionDto)){
            return accountRepository.save(accountDtoToEntity(transactionDto));
        }else{
            return null;
        }
    }

    @Override
    public Mono<Account> findById(String Id) {
        return accountRepository.findById(Id);
    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> update(AccountDto accountDto) {
        return this.accountRepository.findById(accountDto.getId())
                .map(account -> accountDtoToEntity(accountDto))
                .flatMap(this.accountRepository::save);
    }

    @Override
    public Mono<Void> delete(String Id) {
        return this.accountRepository.deleteById(Id);
    }
    public static Account accountDtoToEntity(AccountDto accountDto){
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        return account;
    }
}
