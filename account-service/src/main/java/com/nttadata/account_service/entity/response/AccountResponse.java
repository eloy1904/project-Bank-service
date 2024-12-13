package com.nttadata.account_service.entity.response;


import lombok.Data;

@Data
public class AccountResponse {
    private String id;
    private String accountNumber;
    private double balance;
    private String type; // "savings", "current", "fixed-term"
}
