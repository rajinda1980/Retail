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
public class ItemUpdateDto implements Serializable {
    private Long did;
    private String name;
    private Integer qty;
    private Integer rol;
}
