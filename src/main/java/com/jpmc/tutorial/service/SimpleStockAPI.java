package com.jpmc.tutorial.service;

import com.jpmc.tutorial.exception.SimpleStockException;

/**
 * Created by manish on 9/15/2015.
 */
public interface SimpleStockAPI {
    Double calculateDividendYield(String tickerSymbol)throws SimpleStockException;

    Double calculateProfitEarningRatio(String tickerSymbol)throws SimpleStockException;

    Double calculateStockPrice(String tickerSymbol)throws SimpleStockException;

    Integer calculateShareIndex() throws SimpleStockException;
}
