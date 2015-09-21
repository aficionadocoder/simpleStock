package com.jpmc.tutorial.service.impl;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.service.SimpleStockAPI;
import com.jpmc.tutorial.service.StockService;
import com.jpmc.tutorial.service.impl.calculators.DividendYieldCalculator;
import com.jpmc.tutorial.service.impl.calculators.ProfitEarningRatioCalculator;
import com.jpmc.tutorial.service.impl.calculators.ShareIndexCalculator;
import com.jpmc.tutorial.service.impl.calculators.StockPriceCalculator;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * Created by manish on 9/15/2015.
 */
public class SimpleStockAPIImpl implements SimpleStockAPI {

    private StockService stockService;
    private StockPriceCalculator stockPriceCalculator;
    private ProfitEarningRatioCalculator profitEarningRatioCalculator;
    private ShareIndexCalculator shareIndexCalculator;
    private DividendYieldCalculator dividendYieldCalculator;

    final static Logger logger = Logger.getLogger(SimpleStockAPIImpl.class);


    public SimpleStockAPIImpl(StockService stockService, DividendYieldCalculator dividendYieldCalculator,
                              StockPriceCalculator stockPriceCalculator,
                              ProfitEarningRatioCalculator profitEarningRatioCalculator, ShareIndexCalculator shareIndexCalculator) {
        Validate.notNull(stockService, "Stock Service instance can not be null");
        Validate.notNull(stockPriceCalculator, "StockPriceCalculator instance can not be null");
        Validate.notNull(dividendYieldCalculator, "DividendYieldCalculator instance can not be null");
        Validate.notNull(profitEarningRatioCalculator, "ProfitEarningRatioCalculator instance can not be null");
        Validate.notNull(shareIndexCalculator, "ShareIndexCalculator instance can not be null");

        this.stockService = stockService;
        this.dividendYieldCalculator = dividendYieldCalculator;
        this.stockPriceCalculator = stockPriceCalculator;
        this.profitEarningRatioCalculator = profitEarningRatioCalculator;
        this.shareIndexCalculator = shareIndexCalculator;
        logger.debug("SimpleStockAPIImpl instance is created");
    }

    @Override
    public Double calculateDividendYield(String tickerSymbol) throws SimpleStockException {
        return dividendYieldCalculator.calculate(stockService.getStock(tickerSymbol));
    }

    @Override
    public Double calculateProfitEarningRatio(String tickerSymbol) throws SimpleStockException {
        return profitEarningRatioCalculator.calculate(stockService.getStock(tickerSymbol));
    }

    @Override
    public Double calculateStockPrice(String tickerSymbol) throws SimpleStockException {
        return stockPriceCalculator.calculate(tickerSymbol);
    }

    @Override
    public Integer calculateShareIndex() throws SimpleStockException {
        return shareIndexCalculator.calculate(stockService.getAllStocks());
    }
}
