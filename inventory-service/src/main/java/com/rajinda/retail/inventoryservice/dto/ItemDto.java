package com.rajinda.retail.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {
    private String code;
    private String itemName;
    private Integer qty;
    private Integer rol;
}
