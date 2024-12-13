package com.nttadata.transaction_service.dto.response;

import lombok.Data;

@Data
public class AccountResponse {
    private String id;
    private String accountNumber;
    private double balance;
    private String type;
}
