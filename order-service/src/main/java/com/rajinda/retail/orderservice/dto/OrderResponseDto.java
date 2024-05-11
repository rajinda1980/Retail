package com.rajinda.retail.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto implements Serializable {
    private String orderId;
    private Integer totalQty;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;
    private String createdBy;
    private String status;
}
