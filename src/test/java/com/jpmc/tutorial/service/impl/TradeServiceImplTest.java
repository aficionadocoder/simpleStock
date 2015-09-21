package com.jpmc.tutorial.service.impl;

import com.jpmc.tutorial.cache.Cache;
import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Side;
import com.jpmc.tutorial.model.Trade;
import com.jpmc.tutorial.model.builders.TradeBuilder;
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
public class TradeServiceImplTest {

    private TradeService tradeService;
    private Cache<String, List<Trade>> cache;

    @Test
    public void shouldSuccessfullyAddATrade() throws Exception {
       //Given
        Trade trade= TradeBuilder.getTradeBuilder().withPrice(10.0d)
                .withQuantity(40)
                .withSide(Side.BUY)
                .withTradeTime(new Date())
                .withTickerSymbol("AEL").build();
        Mockito.when(cache.isPresent("AEL")).thenReturn(true);
        Mockito.when(cache.get("AEL")).thenReturn(new ArrayList<Trade>());


        //When
        tradeService.add(trade);

        //Then
        Mockito.verify(cache,Mockito.times(1)).get("AEL");
    }

    @Test
    public void shouldReturnNullWhenTradeIsNotAvailable() throws Exception {
        //When
        Assert.assertTrue(tradeService.getTrades("AEL")==null);
        Mockito.verify(cache,Mockito.times(1)).get("AEL");

    }

    @Test
    public void shouldSuccessfullyFetchTradeForGivenTickerSymbol() throws Exception {
        //When
        tradeService.getTrades("AEL");

        //Then
        Mockito.verify(cache,Mockito.times(1)).get("AEL");

    }

    @Test
    public void shouldSuccessfullyFetchLatestTradesForGivenTickerSymbol() throws Exception {
        //Given
        List<Trade> validTrades = new ArrayList<>();
        Trade expectedTrade = TradeBuilder.getTradeBuilder().withPrice(10.0d)
                .withQuantity(40)
                .withSide(Side.BUY)
                .withTradeTime(new Date())
                .withTickerSymbol("AEL").build();
        validTrades.add(expectedTrade);
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MINUTE,-16);
        validTrades.add(TradeBuilder.getTradeBuilder().withPrice(20.0d)
                .withQuantity(60)
                .withSide(Side.SELL)
                .withTradeTime(cal.getTime())
                .withTickerSymbol("AEL").build());

        Mockito.when(cache.isPresent("AEL")).thenReturn(true);
        Mockito.when(cache.get("AEL")).thenReturn(validTrades);


        //When
        List<Trade> actualTrades=tradeService.getLatestTrades("AEL", 900);

        //Then
        Assert.assertEquals(actualTrades.size(),1);
        Assert.assertTrue(actualTrades.contains(expectedTrade));
    }

    @BeforeClass
    public void BeforeClass() {
        cache= Mockito.mock(Cache.class);
        tradeService=new TradeServiceImpl(cache);
    }

    @BeforeMethod
    public void BeforeMethod() {
        Mockito.reset(cache);
    }
}
