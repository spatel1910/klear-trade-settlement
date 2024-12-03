package com.klear.tradesettlement.service.impl;

import com.klear.tradesettlement.exception.InsufficientBalance;
import com.klear.tradesettlement.exception.InvalidPriceException;
import com.klear.tradesettlement.exception.InvalidQuantityException;
import com.klear.tradesettlement.model.Client;
import com.klear.tradesettlement.model.TradeOrder;
import com.klear.tradesettlement.repository.ClientRepository;
import com.klear.tradesettlement.repository.TradeOrderRepository;
import com.klear.tradesettlement.service.TradeOrderService;
import com.klear.tradesettlement.utils.TradeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    @Autowired
    private TradeOrderRepository tradeOrderRepository;
    @Autowired
    private ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);


    public Mono<TradeOrder> createOrder(TradeOrder tradeOrder) {
        return validate(tradeOrder)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalArgumentException("Invalid trade"));
                    }
                    tradeOrder.setStatus(TradeConstants.PENDING);
                    return tradeOrderRepository.save(tradeOrder)
                            .doOnSuccess(savedOrder -> logger.info("Trade order has been saved: ", savedOrder.getId()))
                            .doOnError(error -> logger.error("Error in creating the order: ", error.getStackTrace()));
                });
    }



    private Mono<Boolean> validate(TradeOrder tradeOrder) {
        BigDecimal requiredBalance = tradeOrder.getPrice().multiply(BigDecimal.valueOf(tradeOrder.getQuantity()));
        String clientId = tradeOrder.getClientId();
        return clientRepository.findById(clientId)
                .flatMap(client -> {
                    if (client == null) {
                        return Mono.error(new RuntimeException("Client does not exist"));
                    }
                    BigDecimal availableBalance = client.getAvailableBalance();
                    if (availableBalance.compareTo(requiredBalance) < 0) {
                        return Mono.error(new InsufficientBalance("Insufficient balance for the transaction"));
                    }
                    if (tradeOrder.getQuantity() <= 0) {
                        return Mono.error(new InvalidQuantityException("Quantity must be positive"));
                    }
                    if (tradeOrder.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                        return Mono.error(new InvalidPriceException("Price must be positive"));
                    }

                    return Mono.just(true);
                });
    }

    @Override
    public Mono<String> getOrderStatus(String id) {
        return tradeOrderRepository.getOrderStatusById(id);

    }
}
