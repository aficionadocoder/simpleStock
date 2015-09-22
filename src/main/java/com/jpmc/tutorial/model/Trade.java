package com.jpmc.tutorial.model;

import org.apache.commons.lang3.Validate;

import java.util.Date;

/**
 * Created by manish on 9/15/2015.
 */
public class Trade{
    private String tickerSymbol;
    private Date tradeTime;
    private Integer quantity;
    private Side side;
    private Double price;

    public Trade(String tickerSymbol, Double price,
                 Side side, Integer quantity, Date tradeTime) {
        Validate.notNull(tickerSymbol, "Ticker Symbol can not be null");
        Validate.notNull(side, "Side can not be null");
        Validate.notNull(tradeTime, "Trade Value can not be null");
        Validate.notNull(price, "Price can not be null");
        Validate.notNull(quantity, "Quantity Value can not be null");
        Validate.isTrue(quantity > 0, "Quantity Value can not be negative or zero");
        Validate.isTrue(Double.compare(price, 0.0) > 0, "Price Value can not be negative");
        this.tickerSymbol = tickerSymbol;
        this.price = price;
        this.side = side;
        this.quantity = quantity;
        this.tradeTime = tradeTime;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public Date getTradeTime() {
        return new Date(this.tradeTime.getTime());
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Side getSide() {
        return side;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (!tickerSymbol.equals(trade.tickerSymbol)) return false;
        if (!tradeTime.equals(trade.tradeTime)) return false;
        if (!quantity.equals(trade.quantity)) return false;
        if (side != trade.side) return false;
        return price.equals(trade.price);

    }

    @Override
    public int hashCode() {
        int result = tickerSymbol.hashCode();
        result = 31 * result + tradeTime.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + side.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", tradeTime=" + tradeTime +
                ", quantity=" + quantity +
                ", side=" + side +
                ", price=" + price +
                '}';
    }
}
