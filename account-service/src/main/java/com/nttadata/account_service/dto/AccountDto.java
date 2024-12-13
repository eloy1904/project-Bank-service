package com.nttadata.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto implements Serializable {
    private String id;
    private String accountNumber;
    private String type;
    private String customerId;
    private double balance;
    private int maxMonthlyTransactions;
    private boolean maintenanceFee;
    private String specificWithdrawalDate;
}
