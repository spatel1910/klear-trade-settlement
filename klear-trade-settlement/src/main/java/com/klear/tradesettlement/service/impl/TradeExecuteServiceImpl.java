package com.klear.tradesettlement.service.impl;

import com.klear.tradesettlement.model.Client;
import com.klear.tradesettlement.model.Exchange;
import com.klear.tradesettlement.model.TradeOrder;
import com.klear.tradesettlement.repository.ClientRepository;
import com.klear.tradesettlement.repository.ExchangeRepository;
import com.klear.tradesettlement.repository.TradeOrderRepository;
import com.klear.tradesettlement.service.TradeExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
public class TradeExecuteServiceImpl implements TradeExecuteService {
    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    private Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(TradeExecuteServiceImpl.class);

    @Override
    public Mono<Void> executeOrder(TradeOrder tradeOrder) {
        selectExchange()
                .doOnNext(data -> {
                    logger.info("Selected Exchange ID" + data.getExchangeId());
                    tradeOrder.setExchangeId(data.getExchangeId());
                    BigDecimal priceBD = tradeOrder.getPrice();
                    double price = priceBD.doubleValue();
                    BigDecimal executionPrice = BigDecimal.valueOf(simulateExecutionPrice(price));
                    BigDecimal usedFund = executionPrice.multiply(BigDecimal.valueOf(tradeOrder.getQuantity()));

                    clientRepository.findClientById(tradeOrder.getClientId())
                            .doOnNext(client -> {
                                BigDecimal availableBalance = client.getAvailableBalance();
                                BigDecimal remainingBalance = availableBalance.subtract(usedFund).setScale(2, RoundingMode.HALF_UP);

                                BigDecimal deviatedBalance = tradeOrder.getPrice().subtract(executionPrice);
                                tradeOrder.setDeviatedBalance(deviatedBalance.setScale(2, RoundingMode.HALF_UP));
                                clientRepository.updateAvailableBalance(remainingBalance, tradeOrder.getClientId())
                                        .doOnSuccess(aVoid -> logger.info("available balance updated successfully"))
                                        .doOnError(aVoid -> {
                                            logger.error("available balance update failed");
                                        })
                                        .subscribe();
                                tradeOrderRepository.updateTradeOrder(tradeOrder.getId(), "EXECUTED", tradeOrder.getExchangeId(), tradeOrder.getDeviatedBalance())
                                        .doOnSuccess(aVoid -> logger.info("trade update has been updated successfully"))
                                        .doOnError(aVoid -> {
                                            logger.error("trade update failed");
                                        })
                                        .subscribe();

                            }).subscribe();

                })
                .subscribe();

        return Mono.empty();
    }


    private Mono<Exchange> selectExchange() {
        return exchangeRepository.fetchAllExchange()
                .collectList()
                .flatMap(data -> {
                    if (data.isEmpty()) {
                        return Mono.error(new RuntimeException("No exchanges available"));
                    }
                    int randomExchange = random.nextInt(data.size());
                    Exchange selectedExchange = data.get(randomExchange);
                    return Mono.just(selectedExchange);
                });
    }

    public double simulateExecutionPrice(double orderPrice) {
        double fluctuation = (random.nextDouble() - 0.5) * 0.1; // ±10% fluctuation
        return orderPrice * (1 + fluctuation);
    }
}
