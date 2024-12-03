package com.klear.tradesettlement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.klear.tradesettlement.utils.IdGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("clients")
public class Client {
    @Id
    @Column("client_id")
    @JsonIgnore
    private String clientId;
    @Column("name")
    private String name;
    @Column("email")
    private String email;

    public Client() {
        this.clientId = IdGenerator.generateClientId();
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Column("available_balance")
    private BigDecimal availableBalance;

    // Getters and setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
