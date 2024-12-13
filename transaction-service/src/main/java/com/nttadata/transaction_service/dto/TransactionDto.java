package com.nttadata.transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto implements Serializable {
    private String transactionId;
    private String accountId;
    private String customerId;
    private double amount;
    private String type;
    private String status;
}
