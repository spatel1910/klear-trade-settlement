package com.klear.tradesettlement.service;

import com.klear.tradesettlement.model.User;
import com.klear.tradesettlement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
