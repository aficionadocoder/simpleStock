package com.jpmc.tutorial.model.builders;

import com.jpmc.tutorial.model.Side;
import com.jpmc.tutorial.model.Trade;

import java.util.Date;
/**
 * Created by manish on 9/15/2015.
 */
public class TradeBuilder {
    private String tickerSymbol;
    private Date tradeTime;
    private Integer quantity;
    private Side side;
    private Double price;

    public TradeBuilder withTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
        return this;
    }

    public TradeBuilder withTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
        return this;
    }

    public TradeBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public TradeBuilder withSide(Side side) {
        this.side = side;
        return this;
    }

    public TradeBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Trade build() {
        return new Trade(tickerSymbol, price, side, quantity, tradeTime);
    }

    public static TradeBuilder getTradeBuilder() {
        return new TradeBuilder();
    }
}
