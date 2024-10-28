package com.klear.tradesettlement.repository;

import com.klear.tradesettlement.model.Client;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client, String> {
    @Modifying
    @Query("INSERT INTO clients (client_id, name, email, available_balance) VALUES (:clientId, :name, :email, :availableBalance)")
    Mono<Void> saveClient(String clientId, String name, String email, BigDecimal availableBalance);

    @Modifying
    @Query("UPDATE clients SET available_balance = :availableBalance WHERE client_id = :clientId")
    Mono<Void> updateAvailableBalance(@Param("availableBalance") BigDecimal availableBalance, @Param("clientId") String clientId);


    @Query("SELECT * FROM clients WHERE CLIENT_ID = :clientId")
    Mono<Client> findClientById(String clientId);

}
