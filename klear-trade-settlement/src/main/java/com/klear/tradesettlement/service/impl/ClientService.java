package com.klear.tradesettlement.service.impl;

import com.klear.tradesettlement.model.Client;
import com.klear.tradesettlement.model.User;
import com.klear.tradesettlement.repository.ClientRepository;
import com.klear.tradesettlement.repository.UserRepository;
import com.klear.tradesettlement.service.TradeOrderService;
import com.klear.tradesettlement.utils.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Mono<Void> createClient(Client client) {
        String username = client.getClientId();
        String plainPassword = client.getClientId();
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        User user=new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        userRepository.addUser(user.getUsername(), user.getPassword()).subscribe();
        return clientRepository.saveClient(client.getClientId(),client.getName(),client.getEmail(),client.getAvailableBalance())
            .doOnSuccess(savedClient -> {
                logger.info("Client has been created: ");
            })
            .doOnError(error -> {
                logger.error("Error creating client: ", error);
            });

    }
}
