package com.klear.tradesettlement.service;

import com.klear.tradesettlement.repository.ShareRepository;
import com.klear.tradesettlement.repository.TradeOrderRepository;
import com.klear.tradesettlement.utils.TradeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SettlementSchedular {

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private ShareRepository shareRepository;
    public static Logger logger= LoggerFactory.getLogger(SettlementSchedular.class);
    @Scheduled(fixedRate = 300000)
    public void settleTrades() {
        System.out.println("Settle Schedular is being called");
        tradeOrderRepository.getClearedTrades(TradeConstants.CLEARED)
                .flatMap(tradeOrder -> {

                    int quantity = tradeOrder.getQuantity();
                    int price = tradeOrder.getPrice().intValue();
                    int deviatedBalance = tradeOrder.getDeviatedBalance().intValue();
                    int totalOrderValue = (price * quantity) + (deviatedBalance);
                    shareRepository.updatePayment(tradeOrder.getClientId(), tradeOrder.getStockSymbol(), totalOrderValue)
                            .doOnSuccess(oVoid -> {
                                logger.info("Amount has been credited to seller account");
                            })
                            .subscribe();
                    tradeOrderRepository.updateTradeStatus(tradeOrder.getId(), TradeConstants.SETTLED)
                            .doOnSuccess(status -> System.out.println("Status updated"))
                            .doOnError(error -> System.out.println("Status update failed "))
                            .subscribe();
                    return null;
                })
                .subscribe();


    }
}
