package com.rajinda.retail.orderservice.advice;

public class OrderException extends RuntimeException {
    public OrderException(String msg) {
        super(msg);
    }
}
