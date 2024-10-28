package com.klear.tradesettlement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("exchanges")
public class Exchange {
    @Column("exchange_id")
    private String exchangeId;

    @Column("name")
    private String name;

    @Column("liquidity")
    private double liquidity;

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLiquidity() {
        return liquidity;
    }

    public void setLiquidity(double liquidity) {
        this.liquidity = liquidity;
    }
}
