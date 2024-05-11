package com.rajinda.retail.inventoryservice.advice;

import com.rajinda.retail.inventoryservice.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class InventoryAdvice {

    public ResponseEntity<Message> handleInventoryException(InventoryException exception) {
        Message message = Message.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(message);
    }
}
