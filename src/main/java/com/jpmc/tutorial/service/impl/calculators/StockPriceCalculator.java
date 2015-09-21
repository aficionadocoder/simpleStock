package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.exception.StockPriceCalculationException;
import com.jpmc.tutorial.model.Trade;
import com.jpmc.tutorial.service.TradeService;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public class StockPriceCalculator {

    public static final int DEFAULT_TIME_IN_SECONDS = 900;
    private TradeService tradeService;
    private Integer timeInSecondsForStockPriceCalculation;
    final static Logger logger = Logger.getLogger(StockPriceCalculator.class);

    public StockPriceCalculator(TradeService tradeService, Integer timeInSecondsForStockPriceCalculation) {
        Validate.notNull(tradeService, "Trade Service instance can not be null");
        Validate.notNull(timeInSecondsForStockPriceCalculation, "Time can not be passed as null");
        Validate.isTrue(timeInSecondsForStockPriceCalculation > 0, "timeInSecondsForStockPriceCalculation can not be negative");
        this.tradeService = tradeService;
        this.timeInSecondsForStockPriceCalculation = timeInSecondsForStockPriceCalculation;
    }

    public StockPriceCalculator(TradeService tradeService) {
        this(tradeService, DEFAULT_TIME_IN_SECONDS);
    }

    public Double calculate(String tickerSymbol) throws SimpleStockException {
        List<Trade> trades = tradeService.getLatestTrades(tickerSymbol, this.timeInSecondsForStockPriceCalculation);
        if (trades == null || trades.size() == 0) {
            logger.error("No Latest Trades for Stock price Calculation");
            throw new StockPriceCalculationException("No Latest Trades for Stock price Calculation");
        }
        Double cumulativePrice = 0d;
        Double cumulativeQuantity = 0d;

        for (Trade trade : trades) {
            cumulativePrice += trade.getPrice() * trade.getQuantity();
            cumulativeQuantity += trade.getQuantity();
        }
        if (cumulativeQuantity == 0 || cumulativePrice == 0) {
            throw new SimpleStockException("Invalid Trades");
        }
        return cumulativePrice / cumulativeQuantity;
    }
}
