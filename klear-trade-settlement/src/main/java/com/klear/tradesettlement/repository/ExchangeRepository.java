package com.klear.tradesettlement.repository;


import com.klear.tradesettlement.model.Exchange;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ExchangeRepository extends ReactiveCrudRepository<Exchange, String> {
    @Query("SELECT * FROM exchanges")
    public Flux<Exchange> fetchAllExchange();
}
