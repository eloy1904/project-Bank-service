package com.nttadata.credit_service.services.implement;

import com.nttadata.credit_service.dto.CreditDto;
import com.nttadata.credit_service.entity.Credit;
import com.nttadata.credit_service.repository.CreditRepository;
import com.nttadata.credit_service.services.CreditService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreditImpl implements CreditService {

    @NonNull
    private final CreditRepository creditRepository;

    @Override
    public Mono<Credit> save(CreditDto creditDto) {
        if(Objects.nonNull(creditDto)){
            return creditRepository.save(creditDtoToEntity(creditDto));
        }else{
            return null;
        }
    }

    @Override
    public Mono<Credit> findById(String Id) {
        return creditRepository.findById(Id);
    }

    @Override
    public Flux<Credit> findAll() {
        return creditRepository.findAll();
    }
    @Override
    public Mono<Credit> udpate(CreditDto creditDto) {
        return this.creditRepository.findById(creditDto.getId())
                .map(credit -> creditDtoToEntity(creditDto))
                .flatMap(this.creditRepository::save);
    }

    @Override
    public Mono<Void> delete(String Id) {
        return creditRepository.deleteById(Id);
    }

    public static Credit creditDtoToEntity(CreditDto creditDto){
        Credit credit = new  Credit();
        BeanUtils.copyProperties(creditDto, credit);
        return credit;
    }
}
