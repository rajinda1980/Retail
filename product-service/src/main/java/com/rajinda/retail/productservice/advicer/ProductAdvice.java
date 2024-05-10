package com.rajinda.retail.productservice.advicer;

import com.rajinda.retail.productservice.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductAdvice {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<Message> productException(ProductException exception) {
        Message msg =
                Message.builder()
                        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(exception.getMessage())
                        .build();
        return ResponseEntity.badRequest().body(msg);
    }
}
