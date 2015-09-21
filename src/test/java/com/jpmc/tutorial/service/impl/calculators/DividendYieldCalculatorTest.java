package com.jpmc.tutorial.service.impl.calculators;

import com.jpmc.tutorial.exception.SimpleStockException;
import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;
import com.jpmc.tutorial.model.builders.StockBuilder;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by manish on 9/15/2015.
 */
public class DividendYieldCalculatorTest {

    private DividendYieldCalculator dividendYieldCalculator;
    private StockPriceCalculator stockPriceCalculator;

    @Test
    public void shouldSuccessfullyCalculateDividendWhenStockTypeIsCommon() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("ALE").withParValue(60d).build();
        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(92.0d));
        Double expectedDividendYield = .25d;

        //When
        Double actualDividendYield = dividendYieldCalculator.calculate(stock);


        //Then
        Assert.assertEquals(actualDividendYield, expectedDividendYield);
    }

    @Test
    public void shouldSuccessfullyCalculateDividendWhenStockTypeIsPreferred() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.PREFERRED)
                .withFixedDividend(2.0d).withTickerSymbol("ALE").withParValue(60d).build();
        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(20.0d));
        Double expectedDividendYield = 0.06d;

        //When
        Double actualDividendYield = dividendYieldCalculator.calculate(stock);


        //Then
        Assert.assertEquals(actualDividendYield, expectedDividendYield);
    }

    @Test(expectedExceptions = {SimpleStockException.class}, expectedExceptionsMessageRegExp = "Fixed Dividend not present for given stock")
    public void shouldThrowAnExceptionWhenStockTypeIsPreferredAndFixedDividendNotSet() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.PREFERRED)
                .withTickerSymbol("ALE").withParValue(60d).build();
        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(20.0d));

        //When
        dividendYieldCalculator.calculate(stock);
    }

    @Test(expectedExceptions = {SimpleStockException.class}, expectedExceptionsMessageRegExp = "Last Dividend not present for given stock")
    public void shouldThrowAnExceptionWhenStockTypeIsCommonAndLastDividendNotSet() throws Exception {
        //Given
        Stock stock = StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withFixedDividend(2.0d)
                .withTickerSymbol("ALE").withParValue(60d).build();
        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(20.0d));

        //When
        dividendYieldCalculator.calculate(stock);
    }

    @BeforeClass
    public void BeforeClass() {
        stockPriceCalculator = Mockito.mock(StockPriceCalculator.class);
        dividendYieldCalculator = new DividendYieldCalculator(stockPriceCalculator);
    }
}
