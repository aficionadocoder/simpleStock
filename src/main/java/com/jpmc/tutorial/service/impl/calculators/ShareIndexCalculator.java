package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.exception.StockPriceCalculationException;
import com.jpmc.tutorial.model.Stock;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Created by manish on 9/15/2015.
 */
public class ShareIndexCalculator {
    private StockPriceCalculator stockPriceCalculator;
    final static Logger logger = Logger.getLogger(ShareIndexCalculator.class);


    public ShareIndexCalculator(StockPriceCalculator stockPriceCalculator) {
        Validate.notNull(stockPriceCalculator, "StockPriceCalculator instance can not be null");
        this.stockPriceCalculator = stockPriceCalculator;
    }

    public Integer calculate(Collection<Stock> stocks) throws SimpleStockException {
        if (stocks == null || stocks.size() == 0) {
            logger.debug("No Stocks for calculating Share Index");
            throw new SimpleStockException("No Stocks for calculating Share Index");
        }
        Double cumPrice = 0d;
        int size = stocks.size();

        for (Stock stock : stocks) {
            try {
                if (cumPrice == 0d) {
                    cumPrice = stockPriceCalculator.calculate(stock.getTickerSymbol());
                } else {
                    cumPrice *= stockPriceCalculator.calculate(stock.getTickerSymbol());
                }
            } catch (StockPriceCalculationException e) {
                logger.error("No Stocks for calculating Share Index", e);
                continue;
            }
        }
        float power = 1 / (float) size;
        Double indexDouble = Math.pow(cumPrice, power);
        return indexDouble.intValue();
    }
}
