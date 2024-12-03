package com.klear.tradesettlement.repository;

import com.klear.tradesettlement.model.Client;
import com.klear.tradesettlement.model.ClientShares;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public interface ShareRepository extends ReactiveCrudRepository<ClientShares, String> {

    @Query("SELECT quantity FROM client_shares WHERE client_id = :clientId AND stock_symbol = :stockSymbol")
    Mono<Integer> getShareQuantity(@Param("clientId") String clientId, @Param("stockSymbol") String stockSymbol);

    @Query("UPDATE client_shares SET quantity = :remainingShareQty WHERE client_id = :clientId AND stock_symbol = :stockSymbol")
    Mono<Void> updateShareQuantity(@Param("clientId") String clientId,
                                   @Param("stockSymbol") String stockSymbol,
                                   @Param("remainingShareQty") Integer remainingShareQty);
    @Query("UPDATE client_shares SET credited_amount = :totalOrderValue WHERE client_id = :clientId AND stock_symbol = :stockSymbol")
    Mono<Void> updatePayment(@Param("clientId") String clientId,
                                   @Param("stockSymbol") String stockSymbol,
                                   @Param("totalOrderValue") Integer totalOrderValue);

}
