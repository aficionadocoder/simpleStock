package com.jpmc.tutorial.service.impl;

import com.jpmc.tutorial.cache.Cache;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;
import com.jpmc.tutorial.model.builders.StockBuilder;
import com.jpmc.tutorial.service.SimpleStockAPI;
import com.jpmc.tutorial.service.StockService;
import com.jpmc.tutorial.service.impl.calculators.DividendYieldCalculator;
import com.jpmc.tutorial.service.impl.calculators.ProfitEarningRatioCalculator;
import com.jpmc.tutorial.service.impl.calculators.ShareIndexCalculator;
import com.jpmc.tutorial.service.impl.calculators.StockPriceCalculator;
import org.hamcrest.CoreMatchers;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public class SimpleStockAPIImplTest {
    private SimpleStockAPI simpleStockAPI;
    private StockService stockService;
    private StockPriceCalculator stockPriceCalculator;
    private ProfitEarningRatioCalculator profitEarningRatioCalculator;
    private ShareIndexCalculator shareIndexCalculator;
    private DividendYieldCalculator dividendYieldCalculator;


    @Test
    public void shouldSuccessfullyCalculateDividendYield() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("AEL").withParValue(60d).build();
        Mockito.when(stockService.getStock("AEL")).thenReturn(stock);


        //When
        simpleStockAPI.calculateDividendYield("AEL");

        //Then
        Mockito.verify(dividendYieldCalculator, Mockito.times(1)).calculate(stock);

    }


    @Test
    public void shouldSuccessfullyCalculatePERatio() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("AEL").withParValue(60d).build();
        Mockito.when(stockService.getStock("AEL")).thenReturn(stock);


        //When
        simpleStockAPI.calculateProfitEarningRatio("AEL");

        //Then
        Mockito.verify(profitEarningRatioCalculator, Mockito.times(1)).calculate(stock);

    }

    @Test
    public void shouldSuccessfullyCalculateStockPrice() throws Exception {
        //When
        simpleStockAPI.calculateStockPrice("AEL");

        //Then
        Mockito.verify(stockPriceCalculator, Mockito.times(1)).calculate("AEL");
    }

    @Test
    public void shouldSuccessfullyCalculateShareIndex() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("AEL").withParValue(60d).build();
        List<Stock> list=new ArrayList<>();
        list.add(stock);
        Mockito.when(stockService.getAllStocks()).thenReturn(list);


        //When
        simpleStockAPI.calculateShareIndex();

        //Then
        Mockito.verify(shareIndexCalculator, Mockito.times(1)).calculate(list);

    }


    @BeforeClass
    public void BeforeClass() {
        stockService = Mockito.mock(StockService.class);
        stockPriceCalculator = Mockito.mock(StockPriceCalculator.class);
        profitEarningRatioCalculator = Mockito.mock(ProfitEarningRatioCalculator.class);
        shareIndexCalculator = Mockito.mock(ShareIndexCalculator.class);
        dividendYieldCalculator = Mockito.mock(DividendYieldCalculator.class);
        simpleStockAPI = new SimpleStockAPIImpl(stockService, dividendYieldCalculator,
                stockPriceCalculator, profitEarningRatioCalculator,
                shareIndexCalculator);
    }
}
