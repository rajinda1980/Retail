package com.rajinda.retail.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String orderId;
    private LocalDateTime createdDate;
    private String createdBy;
    private List<OrderItemDto> items;
}
