package com.klear.tradesettlement.repository;

import com.klear.tradesettlement.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,String> {
    @Query("INSERT INTO user_secrets (username, password) VALUES (:username, :password)")
    Mono<Void> addUser(@Param("username") String username, @Param("password") String password);
    @Query("SELECT * FROM user_secrets WHERE username = :username")
    Mono<User> findByUsername(@Param("username") String username);
}
