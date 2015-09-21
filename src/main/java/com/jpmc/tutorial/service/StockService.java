package com.jpmc.tutorial.service;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Stock;

import java.util.Collection;

/**
 * Created by manish on 9/15/2015.
 */
public interface StockService {

    Stock getStock(String tickerSymbol);

    Collection<Stock> getAllStocks();

    void add(Stock stock);

}
