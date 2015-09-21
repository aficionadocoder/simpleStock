package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.exception.StockPriceCalculationException;
import com.jpmc.tutorial.model.Side;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;
import com.jpmc.tutorial.model.Trade;
import com.jpmc.tutorial.model.builders.StockBuilder;
import com.jpmc.tutorial.model.builders.TradeBuilder;
import com.jpmc.tutorial.service.TradeService;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public class StockPriceCalculatorTest {
    private TradeService tradeService;
    private StockPriceCalculator stockPriceCalculator;

    @Test
    public void shouldSuccessfullyCalculateStockPriceWhenLatestTradesAreValid() throws Exception {
        //Given
        List<Trade> validTrades = new ArrayList<>();
        validTrades.add(TradeBuilder.getTradeBuilder().withPrice(10.0d)
                .withQuantity(40)
                .withSide(Side.BUY)
                .withTradeTime(new Date())
                .withTickerSymbol("AEL").build());
        validTrades.add(TradeBuilder.getTradeBuilder().withPrice(20.0d)
                .withQuantity(60)
                .withSide(Side.SELL)
                .withTradeTime(new Date())
                .withTickerSymbol("AEL").build());
        Double expectedStockPrice = 16.0d;

        Mockito.when(tradeService.getLatestTrades("ALE", 900)).thenReturn(validTrades);

        //When
        Double actualStockPrice = stockPriceCalculator.calculate("ALE");

        //Then
        Assert.assertEquals(actualStockPrice, expectedStockPrice);
    }


    @Test(expectedExceptions = { StockPriceCalculationException.class }, expectedExceptionsMessageRegExp = "No Latest Trades for Stock price Calculation")
    public void shouldThrowAnExceptionWhenThereIsNoLatestTrades() throws Exception{
        //Given
        List<Trade> validTrades = new ArrayList<>();
        Mockito.when(tradeService.getLatestTrades("ALE", 900)).thenReturn(validTrades);

        //When
        stockPriceCalculator.calculate("ALE");
    }

    @Test(expectedExceptions = { IllegalArgumentException.class }, expectedExceptionsMessageRegExp = "Quantity Value can not be negative or zero")
    public void shouldThrowAnExceptionWhenThereIsTradeWithInValidValue() throws Exception{
        //Given
        List<Trade> validTrades = new ArrayList<>();
        validTrades.add(TradeBuilder.getTradeBuilder().withPrice(20.0d)
                .withQuantity(0)
                .withSide(Side.SELL)
                .withTradeTime(new Date())
                .withTickerSymbol("AEL").build());
        Mockito.when(tradeService.getLatestTrades("ALE", 900)).thenReturn(validTrades);

        //When
        stockPriceCalculator.calculate("ALE");
    }

    @Test(expectedExceptions = { IllegalArgumentException.class }, expectedExceptionsMessageRegExp = "Price Value can not be negative")
    public void shouldThrowAnExceptionWhenThereIsTradeWithInValidQuantityValue() throws Exception{
        //Given
        List<Trade> validTrades = new ArrayList<>();
        validTrades.add(TradeBuilder.getTradeBuilder().withPrice(-20.0d)
                .withSide(Side.SELL)
                .withQuantity(10)
                .withTradeTime(new Date())
                .withTickerSymbol("AEL").build());
        Mockito.when(tradeService.getLatestTrades("ALE", 900)).thenReturn(validTrades);

        //When
        stockPriceCalculator.calculate("ALE");
    }

    @BeforeClass
    public void BeforeClass() {
        tradeService = Mockito.mock(TradeService.class);
        stockPriceCalculator = new StockPriceCalculator(tradeService);
    }
}
