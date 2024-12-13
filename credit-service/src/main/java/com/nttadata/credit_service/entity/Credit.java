package com.nttadata.credit_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "credits")
public class Credit {
    @Id
    private String id;
    private String type; // "personal", "business", "credit-card"
    private String customerId; // Relación con el cliente
    private double creditLimit; // Para tarjetas de crédito
    private double balance; // Monto utilizado o saldo actual
    private boolean active; // Estado del crédito
    private double interestRate;
    private String createdAt; //Auditoria
    private String updatedAt; //Auditoria
}
