package com.jpmc.tutorial.service.impl;

import com.jpmc.tutorial.cache.Cache;
import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.service.StockService;
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Created by manish on 9/15/2015.
 */
public class StockServiceImpl implements StockService {

    private Cache<String, Stock> stockCache;
    final static Logger logger = Logger.getLogger(StockServiceImpl.class);

    public StockServiceImpl(Cache<String, Stock> stockCache)
    {
        Validate.notNull(stockCache, "Stock cache instance can not be null");
        this.stockCache = stockCache;
    }

    @Override
    public Stock getStock(String tickerSymbol) {
        logger.debug("getting Stock for ticker" + tickerSymbol);
        return stockCache.get(tickerSymbol);
    }

    @Override
    public Collection<Stock> getAllStocks() {
        return stockCache.values();
    }

    @Override
    public void add(Stock stock) {
        logger.debug("Adding Stock " + stock.toString());
        stockCache.put(stock.getTickerSymbol(), stock);
    }
}
