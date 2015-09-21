package com.jpmc.tutorial.model.builders;

import com.jpmc.tutorial.model.Stock;
import com.jpmc.tutorial.model.StockType;

/**
 * Created by manish on 9/15/2015.
 */
public class StockBuilder {
    private String tickerSymbol;
    private StockType stockType;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;


    public StockBuilder withTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
        return this;
    }

    public StockBuilder withStockType(StockType stockType) {
        this.stockType = stockType;
        return this;
    }

    public StockBuilder withLastDividend(Double lastDividend) {
        this.lastDividend = lastDividend;
        return this;
    }

    public StockBuilder withFixedDividend(Double fixedDividend) {
        this.fixedDividend = fixedDividend;
        return this;
    }

    public StockBuilder withParValue(Double parValue) {
        this.parValue = parValue;
        return this;
    }

    public Stock build(){
        return new Stock(tickerSymbol,stockType,lastDividend,fixedDividend,parValue);
    }

    public static StockBuilder getStockBuilder(){
        return new StockBuilder();
    }
}
