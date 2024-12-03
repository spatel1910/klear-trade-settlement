package com.klear.tradesettlement.repository;


import com.klear.tradesettlement.model.TradeOrder;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public interface TradeOrderRepository extends ReactiveCrudRepository<TradeOrder, String> {
    @Query("UPDATE trade_orders SET status = :status, exchange_id = :exchangeId, deviated_balance = :deviatedBalance WHERE id = :id")
    Mono<Integer> updateTradeOrder(@Param("id") String id,
                                   @Param("status") String status,
                                   @Param("exchangeId") String exchangeId,
                                   @Param("deviatedBalance") BigDecimal deviatedBalance);

    @Query("SELECT status FROM trade_orders WHERE id = :id")
    Mono<String> getOrderStatusById(@Param("id") String id);

    @Query("SELECT * FROM trade_orders WHERE status = :status")
    Flux<TradeOrder> getExecutedTrades(@Param("status") String status);
    @Query("UPDATE trade_orders SET status = :status WHERE id = :id")
    Mono<Void> updateTradeStatus(@Param("id") String id, @Param("status") String status);

    @Query("SELECT * FROM trade_orders WHERE status = :status")
    Flux<TradeOrder> getClearedTrades(@Param("status") String status);
}

