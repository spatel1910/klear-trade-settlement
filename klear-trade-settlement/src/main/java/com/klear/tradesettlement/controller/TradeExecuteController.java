package com.klear.tradesettlement.controller;

import com.klear.tradesettlement.model.Exchange;
import com.klear.tradesettlement.model.TradeOrder;
import com.klear.tradesettlement.repository.ExchangeRepository;
import com.klear.tradesettlement.service.TradeExecuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class TradeExecuteController {
    @Autowired
    private TradeExecuteService tradeExecuteService;
    @Autowired
    private KafkaTemplate<String, TradeOrder> kafkaTemplate;
    @Autowired
    private ExchangeRepository exchangeRepository;
    private static final Logger logger = LoggerFactory.getLogger(TradeOrderController.class);

    @KafkaListener(topics = "trade-events",groupId = "trade-order-consumer-group")
    public void executeOrder(TradeOrder tradeOrder) {
        logger.info("Received request to execute order: {}", tradeOrder);
       tradeExecuteService.executeOrder(tradeOrder);


    }
}
