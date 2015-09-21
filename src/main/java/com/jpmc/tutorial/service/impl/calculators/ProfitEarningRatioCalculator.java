package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Stock;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * Created by manish on 9/15/2015.
 */
public class ProfitEarningRatioCalculator {
    private StockPriceCalculator stockPriceCalculator;
    final static Logger logger = Logger.getLogger(ProfitEarningRatioCalculator.class);

    public ProfitEarningRatioCalculator(StockPriceCalculator stockPriceCalculator) {
        Validate.notNull(stockPriceCalculator, "StockPriceCalculator instance can not be null");
        this.stockPriceCalculator = stockPriceCalculator;
    }

    public Double calculate(Stock stock) throws SimpleStockException {
        if (stock == null) {
            logger.error("Stock instance can not be null");
            throw new SimpleStockException("Stock instance can not be null");
        }
        Double tickerPrice = stockPriceCalculator.calculate(stock.getTickerSymbol());
        Double lastDividend = stock.getLastDividend();
        if (lastDividend == null) {
            logger.error("Last Dividend not present for given stock");
            throw new SimpleStockException("Last Dividend not present for given stock");
        }
        return tickerPrice / lastDividend;
    }
}
