package com.jpmc.tutorial.service;

import com.jpmc.tutorial.cache.CacheImpl;
import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Side;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;
import com.jpmc.tutorial.model.Trade;
import com.jpmc.tutorial.model.builders.StockBuilder;
import com.jpmc.tutorial.model.builders.TradeBuilder;
import com.jpmc.tutorial.service.impl.SimpleStockAPIImpl;
import com.jpmc.tutorial.service.impl.StockServiceImpl;
import com.jpmc.tutorial.service.impl.TradeServiceImpl;
import com.jpmc.tutorial.service.impl.calculators.DividendYieldCalculator;
import com.jpmc.tutorial.service.impl.calculators.ProfitEarningRatioCalculator;
import com.jpmc.tutorial.service.impl.calculators.ShareIndexCalculator;
import com.jpmc.tutorial.service.impl.calculators.StockPriceCalculator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public class EndToEndStockServiceAcceptanceTest {

    private StockService stockService;
    private TradeService tradeService;
    private SimpleStockAPI simpleStockAPI;

    @Test
    public void shouldSuccessfullyTestEndToEndFunctinalityOfSimpleStockService() throws Exception {
        //Given
        Double lastDividend = 23d;
        createStockData(23d);
        Double price = 10.0d;
        Integer quantity = 40;

        Trade expectedTrade = TradeBuilder.getTradeBuilder().withPrice(price)
                .withQuantity(quantity)
                .withSide(Side.BUY)
                .withTradeTime(new Date())
                .withTickerSymbol("ALE").build();
        createTradeData(expectedTrade);
        Double expectedStockPrice = (price * quantity / quantity);
        Double expectedDividendYieldALE = lastDividend / expectedStockPrice;
        Double expectedProfitEarningRatioALE = expectedStockPrice / lastDividend;
        Integer expectedIndex = expectedStockPrice.intValue();

        //When
        Double actualStockPrice = simpleStockAPI.calculateStockPrice("ALE");
        //Then
        Assert.assertEquals(actualStockPrice, expectedStockPrice);


        //When
        Double actualDividendYieldALE = simpleStockAPI.calculateDividendYield("ALE");
        //Then
        Assert.assertEquals(actualDividendYieldALE, expectedDividendYieldALE);


        try {
            //When
            simpleStockAPI.calculateDividendYield("AEL");
        } catch (SimpleStockException e) {
            //Then
            Assert.assertEquals(e.getMessage(), "Stock instance can not be null");
        }

        //When
        Double actualProfitEarningRatioALE = simpleStockAPI.calculateProfitEarningRatio("ALE");
        //Then
        Assert.assertEquals(actualProfitEarningRatioALE, expectedProfitEarningRatioALE);

        //When
        Integer index = simpleStockAPI.calculateShareIndex();
        Assert.assertEquals(index, expectedIndex);

        //When
        createMoreStockData();
        Integer expectedIndexVal = 6;
        Integer index2 = simpleStockAPI.calculateShareIndex();
        Assert.assertEquals(index2, expectedIndexVal);

    }

    private void createTradeData(Trade expectedTrade) {
        tradeService.add(expectedTrade);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -16);
        tradeService.add(TradeBuilder.getTradeBuilder().withPrice(20.0d)
                .withQuantity(60)
                .withSide(Side.BUY)
                .withTradeTime(cal.getTime())
                .withTickerSymbol("POP").build());
        tradeService.add(TradeBuilder.getTradeBuilder().withPrice(200.0d)
                .withQuantity(43)
                .withSide(Side.BUY)
                .withTradeTime(new Date())
                .withTickerSymbol("TEA").build());
        tradeService.add(TradeBuilder.getTradeBuilder().withPrice(20.0d)
                .withQuantity(60)
                .withSide(Side.SELL)
                .withTradeTime(cal.getTime())
                .withTickerSymbol("ALE").build());
    }

    private void createStockData(Double lastDividend) {
        stockService.add(StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(lastDividend).withTickerSymbol("ALE").withParValue(60d).build());
    }

    private void createMoreStockData() {
        stockService.add(StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(0.0d).withTickerSymbol("TEA").withParValue(100d).build());
        stockService.add(StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(8.0d).withTickerSymbol("POP").withParValue(100d).build());
        stockService.add(StockBuilder.getStockBuilder().withStockType(StockType.PREFERRED)
                .withLastDividend(8.0d).withFixedDividend(2.0d).
                        withTickerSymbol("GIN").withParValue(100d).build());
    }


    @BeforeClass
    public void BeforeClass() {
        CacheImpl<String, Stock> stockCache = new CacheImpl<>();
        stockService = new StockServiceImpl(stockCache);
        CacheImpl<String, List<Trade>> tradeCache = new CacheImpl<>();
        tradeService = new TradeServiceImpl(tradeCache);
        StockPriceCalculator stockPriceCalculator = new StockPriceCalculator(tradeService);
        simpleStockAPI = new SimpleStockAPIImpl(stockService,
                new DividendYieldCalculator(stockPriceCalculator), stockPriceCalculator,
                new ProfitEarningRatioCalculator(stockPriceCalculator),
                new ShareIndexCalculator(stockPriceCalculator)
        );
    }
}
