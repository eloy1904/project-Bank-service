package com.nttadata.account_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "account")
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private String type; // "savings", "current", "fixed-term"
    private String customerId; // Relación con el cliente
    private double balance;
    private int maxMonthlyTransactions; // Solo para cuentas de ahorro
    private boolean maintenanceFee; // Aplica para cuentas corrientes
    private String specificWithdrawalDate; // Solo para plazo fijo
}
