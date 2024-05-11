package com.rajinda.retail.orderservice.advice;

import com.rajinda.retail.orderservice.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderAdvice {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Message> handleOrderException(OrderException orderException) {
        Message message =
                Message.builder()
                        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(orderException.getMessage())
                        .build();
        return ResponseEntity.badRequest().body(message);
    }
}
