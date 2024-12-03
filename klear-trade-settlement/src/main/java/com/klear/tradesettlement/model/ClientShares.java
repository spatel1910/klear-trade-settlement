package com.klear.tradesettlement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("client_shares")
public class ClientShares {

    @Column("client_id")
    private String clientId;
    @Column("stock_symbol")
    private String stockSymbol;

    @Column("quantity")
    private int quantity;
    @Column("credited_amount")
    private int creditedAmount;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCreditedAmount() {
        return creditedAmount;
    }

    public void setCreditedAmount(int creditedAmount) {
        this.creditedAmount = creditedAmount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
