package com.jpmc.tutorial.service;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Trade;

import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public interface TradeService {

    void add(Trade trade);

    List<Trade> getTrades(String tickerSymbol);

    /*
     Fetch Latest Trades received in the system within given time -timeInSeconds and given ticker Symbol
     */
    List<Trade> getLatestTrades(String tickerSymbol, Integer timeInSeconds );
}
