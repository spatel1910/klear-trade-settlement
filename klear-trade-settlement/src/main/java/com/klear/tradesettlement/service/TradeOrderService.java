package com.klear.tradesettlement.service;

import com.klear.tradesettlement.model.TradeOrder;
import reactor.core.publisher.Mono;

import java.util.Optional;

 public interface TradeOrderService {
     Mono<TradeOrder> createOrder(TradeOrder tradeOrder);
     Mono<String> getOrderStatus(String id);
}
