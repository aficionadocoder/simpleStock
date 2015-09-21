package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Stock;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * Created by manish on 9/15/2015.
 */
public class DividendYieldCalculator {

    private StockPriceCalculator stockPriceCalculator;
    final static Logger logger = Logger.getLogger(DividendYieldCalculator.class);

    public DividendYieldCalculator(StockPriceCalculator stockPriceCalculator) {
        Validate.notNull(stockPriceCalculator, "StockPriceCalculator instance can not be null");
        this.stockPriceCalculator = stockPriceCalculator;
    }

    public Double calculate(Stock stock) throws SimpleStockException {
        if (stock == null) {
            logger.error("Stock instance can not be null");
            throw new SimpleStockException("Stock instance can not be null");
        }
        return calculateYield(stock, stockPriceCalculator.calculate(stock.getTickerSymbol()));
    }

    //Implementation as per given data where Fixed Dividend is given in percentage
    // and Last Dividend in concrete value
    private Double calculateYield(Stock stock, Double tickerPrice) throws SimpleStockException {
        Double dividendYield = 0d;
        switch (stock.getStockType()) {
            case COMMON:
                Double lastDividend = stock.getLastDividend();
                if (lastDividend == null) {
                    logger.error("Last Dividend not present for given stock");
                    throw new SimpleStockException("Last Dividend not present for given stock");
                }
                dividendYield = lastDividend / tickerPrice;
                break;
            case PREFERRED:
                Double fixedDividend = stock.getFixedDividendInPercentage();
                if (fixedDividend == null) {
                    logger.error("Fixed Dividend not present for given stock");
                    throw new SimpleStockException("Fixed Dividend not present for given stock");
                }
                dividendYield = (fixedDividend * stock.getParValue()) / (100 * tickerPrice);
                break;
            default:
                logger.error("Unknown Stock Type");
                throw new SimpleStockException("Unknown Stock Type");
        }
        return dividendYield;
    }
}
