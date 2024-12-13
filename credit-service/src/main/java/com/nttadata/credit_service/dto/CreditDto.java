package com.nttadata.credit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditDto implements Serializable {
    private String id;
    private String type; // "personal", "business", "credit-card"
    private String customerId; // Relación con el cliente
    private double creditLimit; // Para tarjetas de crédito
    private double balance; // Monto utilizado o saldo actual
    private boolean active; // Estado del crédito
    private double interestRate;
}
