package com.jpmc.tutorial.exception;

/**
 * Created by manish on 9/18/2015.
 */
public class StockPriceCalculationException extends SimpleStockException {
    public StockPriceCalculationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public StockPriceCalculationException(Throwable cause) {
        super(cause);
    }

    public StockPriceCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockPriceCalculationException(String message) {
        super(message);
    }
}
