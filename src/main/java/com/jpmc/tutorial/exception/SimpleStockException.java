package com.jpmc.tutorial.exception;

/**
 * Created by manish on 9/15/2015.
 */
public class SimpleStockException extends Exception {

    public SimpleStockException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SimpleStockException(Throwable cause) {
        super(cause);
    }

    public SimpleStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleStockException(String message) {
        super(message);
    }
}
