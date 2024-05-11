package com.rajinda.retail.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto implements Serializable {
    private Long did;
    private String orderId;
    private LocalDateTime createdDate;
    private String createdBy;
    private String status;
    private Set<OrderItemDto> items;

}
