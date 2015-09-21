package com.jpmc.tutorial.service.impl;

import com.jpmc.tutorial.cache.Cache;
import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Trade;
import com.jpmc.tutorial.service.TradeService;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */

public class TradeServiceImpl implements TradeService {

    private Cache<String, List<Trade>> tradeCache;
    final static Logger logger = Logger.getLogger(TradeServiceImpl.class);

    public TradeServiceImpl(Cache<String, List<Trade>> tradeCache) {
        Validate.notNull(tradeCache, "Trade cache instance can not be null");
        this.tradeCache = tradeCache;
    }

    @Override
    public void add(Trade trade) {
        synchronized (this) {
            List<Trade> trades = null;
            String tickerSymbol = trade.getTickerSymbol();
            if (!tradeCache.isPresent(tickerSymbol)) {
                trades = new ArrayList<>();
                trades.add(trade);
                tradeCache.put(tickerSymbol, trades);
            } else {
                trades = tradeCache.get(tickerSymbol);
                trades.add(trade);
            }
        }
        logger.debug("Added trade in cache"+trade.toString());
    }


    @Override
    public List<Trade> getTrades(String tickerSymbol) {
        return tradeCache.get(tickerSymbol);
    }

    @Override
    public List<Trade> getLatestTrades(String tickerSymbol, Integer timeInSeconds) {
        List<Trade> trades = getTrades(tickerSymbol);
        return (trades == null || trades.size() == 0) ? new ArrayList<Trade>() : filterBasedOnTime(trades, timeInSeconds);
    }

    private List<Trade> filterBasedOnTime(List<Trade> trades, Integer timeInSeconds) {
        List<Trade> latestTrades = new ArrayList<>();
        int size = trades.size();
        for (int i = size - 1; i >= 0; i--) {
            Trade trade = trades.get(i);
            long tradeTimeInMilli = trade.getTradeTime().getTime();
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            long nowInMilli = timeInMillis;
            if (((nowInMilli - tradeTimeInMilli) / 1000) < timeInSeconds) {
                latestTrades.add(trade);
            }
        }
        logger.debug("Latest trades fetched="+latestTrades.toString());
        return latestTrades;
    }
}
