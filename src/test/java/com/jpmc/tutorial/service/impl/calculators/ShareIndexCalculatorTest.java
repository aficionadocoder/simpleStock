package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;
import com.jpmc.tutorial.model.builders.StockBuilder;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public class ShareIndexCalculatorTest {
    private StockPriceCalculator stockPriceCalculator;
    private ShareIndexCalculator shareIndexCalculator;


    @Test
    public void shouldSuccessfullyCalculateShareIndexForGivenStocks() throws Exception {
        //Given
        List<Stock> validStocks = new ArrayList<>();
        validStocks.add(StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("ALE").withParValue(60d).build());
        validStocks.add(StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("GOOG").withParValue(60d).build());
        validStocks.add(StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("ADR").withParValue(60d).build());

        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(500.0d));
        Mockito.when(stockPriceCalculator.calculate("GOOG")).thenReturn(new Double(100.0d));
        Mockito.when(stockPriceCalculator.calculate("ADR")).thenReturn(new Double(20.0d));
        Integer expectedIndexValue = 100;

        //When
        Integer actualIndexValue = shareIndexCalculator.calculate(validStocks);

        //Then
        Assert.assertEquals(actualIndexValue, expectedIndexValue);

    }


    @Test(expectedExceptions = { SimpleStockException.class }, expectedExceptionsMessageRegExp = "No Stocks for calculating Share Index")
    public void shouldThrowSimpleStockExceptionWhenCalculatingIndexByUsingBlankListOfStocks() throws Exception {
        //Given
        List<Stock> validStocks = new ArrayList<>();

        //When
        shareIndexCalculator.calculate(validStocks);
    }

    @Test(expectedExceptions = { SimpleStockException.class }, expectedExceptionsMessageRegExp = "No Stocks for calculating Share Index")
    public void shouldThrowSimpleStockExceptionWhenCalculatingIndexByUsingNullListOfStocks() throws Exception {
        //Given
        List<Stock> validStocks = null;

        //When
        shareIndexCalculator.calculate(validStocks);
    }

    @BeforeClass
    public void BeforeClass() {
        stockPriceCalculator = Mockito.mock(StockPriceCalculator.class);
        shareIndexCalculator = new ShareIndexCalculator(stockPriceCalculator);
    }
}

