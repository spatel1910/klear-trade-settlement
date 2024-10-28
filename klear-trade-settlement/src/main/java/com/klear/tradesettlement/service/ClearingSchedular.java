package com.klear.tradesettlement.service;

import com.klear.tradesettlement.repository.ShareRepository;
import com.klear.tradesettlement.repository.TradeOrderRepository;
import com.klear.tradesettlement.utils.TradeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ClearingSchedular {

    @Autowired
    private TradeOrderRepository tradeOrderRepository;
    @Autowired
    private ShareRepository shareRepository;

    public static Logger logger=LoggerFactory.getLogger(ClearingSchedular.class);
    @Scheduled(fixedRate = 300000)
    public void clearTrades() {
        logger.info("Clearing Scheduler is Triggered");
        tradeOrderRepository.getExecutedTrades(TradeConstants.EXECUTED)
                .flatMap(tradeOrder -> {
                    logger.info("Trade Order "+tradeOrder);
                    shareRepository.getShareQuantity(tradeOrder.getClientId(), tradeOrder.getStockSymbol())
                            .flatMap(shareQty -> {
                                if (shareQty > tradeOrder.getQuantity()) {
                                    Integer remainingShareQty = shareQty - tradeOrder.getQuantity();
                                    shareRepository.updateShareQuantity(tradeOrder.getClientId(), tradeOrder.getStockSymbol(), remainingShareQty)
                                            .doOnSuccess(status -> {
                                                logger.info("Share Qty has been Updated");
                                            })
                                            .subscribe();

                                }
                                return Mono.empty();
                            })
                            .subscribe();

                    tradeOrderRepository.updateTradeStatus(tradeOrder.getId(), TradeConstants.CLEARED)
                            .doOnSuccess(status -> logger.info("Status updated"))
                            .doOnError(error -> logger.error("Status update failed "))
                            .subscribe();
                    return null;
                })
                .subscribe();


    }
}
