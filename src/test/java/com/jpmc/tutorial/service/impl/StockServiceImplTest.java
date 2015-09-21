package com.jpmc.tutorial.service.impl;

import com.jpmc.tutorial.cache.Cache;
import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Side;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;
import com.jpmc.tutorial.model.Trade;
import com.jpmc.tutorial.model.builders.StockBuilder;
import com.jpmc.tutorial.model.builders.TradeBuilder;
import com.jpmc.tutorial.service.StockService;
import com.jpmc.tutorial.service.TradeService;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public class StockServiceImplTest {
    private StockService stockService;
    private Cache<String, Stock> cache;

    @Test
    public void shouldSuccessfullyAddAStock() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("AEL").withParValue(60d).build();

        //When
        stockService.add(stock);

        //Then
        Mockito.verify(cache, Mockito.times(1)).put("AEL", stock);
    }

    @Test
    public void shouldReturnNullWhenStockIsNotAvailable() throws Exception {
        //Then
        Assert.assertTrue(stockService.getStock("AEL") == null);

        Mockito.verify(cache, Mockito.times(1)).get("AEL");

    }

    @Test
    public void shouldSuccessfullyFetchStockForGivenTickerSymbol() throws Exception {
        //Given
        Mockito.when(cache.isPresent("AEL")).thenReturn(true);


        //When
        stockService.getStock("AEL");

        //Then
        Mockito.verify(cache, Mockito.times(1)).get("AEL");

    }

    @Test
    public void shouldSuccessfullyFetchAllStocks() throws Exception {
        //When
        stockService.getAllStocks();

        //Then
        Mockito.verify(cache, Mockito.times(1)).values();
    }

    @BeforeClass
    public void BeforeClass() {
        cache = Mockito.mock(Cache.class);
        stockService = new StockServiceImpl(cache);
    }

    @BeforeMethod
    public void BeforeMethd() {
       Mockito.reset(cache);
    }
}

