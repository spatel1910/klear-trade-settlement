package com.klear.tradesettlement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.klear.tradesettlement.utils.IdGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Table("trade_orders")
public class TradeOrder   {


    @Column("id")
    @JsonIgnore
    private String id; // Auto-generated ID

    @Column("client_id")
    private String clientId; // Client ID
    @Column("stock_symbol")
    private String stockSymbol; // Stock symbol

    @Column("quantity")
    private int quantity; // Quantity of stocks
    @Column("price")
    private BigDecimal price; // Price per stock
    @Column("status")
    private String status; // Status of the order (e.g., PENDING, EXECUTED)
    @Column("exchange_id")
    @JsonIgnore
    private String exchangeId;
    @Column("deviated_balance")
    @JsonIgnore
    private BigDecimal deviatedBalance;
    @Column("seller_id")
    private String sellerId;
    public TradeOrder() {
        this.id = IdGenerator.generateTradeId(); // Generate custom ID on creation
    }
    @JsonCreator
    public TradeOrder(
            @JsonProperty("id") String id,
            @JsonProperty("clientId") String clientId,
            @JsonProperty("stockSymbol") String stockSymbol,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("status") String status,
            @JsonProperty("exchangeId") String exchangeId,
            @JsonProperty("deviatedBalance") BigDecimal deviatedBalance,
            @JsonProperty("sellerId") String sellerId
    ) {
        this.id = id != null ? id : IdGenerator.generateTradeId(); // Use existing ID or generate a new one
        this.clientId = clientId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.exchangeId = exchangeId;
        this.deviatedBalance = deviatedBalance;
        this.sellerId=sellerId;
    }


    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public BigDecimal getDeviatedBalance() {
        return deviatedBalance;
    }

    public void setDeviatedBalance(BigDecimal deviatedBalance) {
        this.deviatedBalance = deviatedBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}

