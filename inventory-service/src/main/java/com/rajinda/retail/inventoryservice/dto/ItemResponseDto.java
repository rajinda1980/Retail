package com.rajinda.retail.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto implements Serializable {
    private Long did;
    private String code;
    private String itemName;
    private Integer qty;
    private Integer rol;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
