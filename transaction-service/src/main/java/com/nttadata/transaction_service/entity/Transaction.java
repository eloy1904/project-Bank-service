package com.nttadata.transaction_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Transaction")
public class Transaction {
    @Id
    private String transactionId;
    private String accountId;
    private String customerId;
    private double amount;
    private String type; // "deposit", "withdrawal"
    private String status; // "completed", "failed"
}
