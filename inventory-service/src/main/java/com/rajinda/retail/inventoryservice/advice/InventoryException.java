package com.rajinda.retail.inventoryservice.advice;

public class InventoryException extends RuntimeException {
    public InventoryException(String msg) {
        super(msg);
    }
}
