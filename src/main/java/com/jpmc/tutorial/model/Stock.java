package com.jpmc.tutorial.model;

import org.apache.commons.lang3.Validate;

import java.io.Serializable;

/**
 * Created by manish on 9/15/2015.
 */
public class Stock implements Serializable {
    private String tickerSymbol;
    private StockType stockType;
    private Double lastDividend;
    private Double fixedDividendInPercentage;
    private Double parValue;


    public Stock(String tickerSymbol, StockType stockType,
                 Double lastDividend, Double fixedDividendInPercentage, Double parValue) {
        Validate.notNull(tickerSymbol, "Ticker Symbol can not be null");
        Validate.notNull(stockType, "Stock Type can not be null");
        Validate.notNull(parValue, "Par Value can not be null");
        Validate.isTrue(Double.compare(parValue, 0.0) >= 0, "Par Value can not be negative");

        this.tickerSymbol = tickerSymbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividendInPercentage = fixedDividendInPercentage;
        this.parValue = parValue;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public Double getFixedDividendInPercentage() {
        return fixedDividendInPercentage;
    }

    public Double getParValue() {
        return parValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        return tickerSymbol.equals(stock.tickerSymbol);

    }

    @Override
    public int hashCode() {
        return tickerSymbol.hashCode();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", stockType=" + stockType +
                ", lastDividend=" + lastDividend +
                ", fixedDividendInPercentage=" + fixedDividendInPercentage +
                ", parValue=" + parValue +
                '}';
    }
}
