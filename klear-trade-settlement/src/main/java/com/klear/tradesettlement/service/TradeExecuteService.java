package com.klear.tradesettlement.service;

import com.klear.tradesettlement.model.TradeOrder;
import reactor.core.publisher.Mono;


public interface TradeExecuteService {
    public Mono<Void> executeOrder(TradeOrder tradeOrder);
}
