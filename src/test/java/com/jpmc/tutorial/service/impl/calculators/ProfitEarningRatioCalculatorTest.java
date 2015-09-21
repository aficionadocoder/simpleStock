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
public class ProfitEarningRatioCalculatorTest {

    private ProfitEarningRatioCalculator profitEarningRatioCalculator;
    private StockPriceCalculator stockPriceCalculator;

    @Test
    public void shouldSuccessfullyCalculateDividendWhenStockIsFullyObjectIsFullyPopulated() throws Exception{
        //Given
        Stock stock= StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withLastDividend(23.0d).withTickerSymbol("ALE").withParValue(60d).build();
        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(92.0d));
        Double expectedPERatio=4.0d;

        //When
        Double actualPERatio=profitEarningRatioCalculator.calculate(stock);

        //Then
        Assert.assertEquals(actualPERatio, expectedPERatio);
    }

    @Test(expectedExceptions = { SimpleStockException.class }, expectedExceptionsMessageRegExp = "Last Dividend not present for given stock")
    public void shouldThrowAnExceptionWhenLastDividendNotSet() throws Exception{
        //Given
        Stock stock= StockBuilder.getStockBuilder().withStockType(StockType.COMMON)
                .withFixedDividend(2.0d)
                .withTickerSymbol("ALE").withParValue(60d).build();
        Mockito.when(stockPriceCalculator.calculate("ALE")).thenReturn(new Double(20.0d));

        //When
        profitEarningRatioCalculator.calculate(stock);
    }

    @BeforeClass
    public void BeforeClass(){
        stockPriceCalculator= Mockito.mock(StockPriceCalculator.class);
        profitEarningRatioCalculator=new ProfitEarningRatioCalculator(stockPriceCalculator);
    }
}
