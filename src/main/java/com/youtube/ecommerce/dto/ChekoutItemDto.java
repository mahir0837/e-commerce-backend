package com.youtube.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChekoutItemDto {

    private String productName;
    private int quantity;
    private double amount;
    private Long productID;
    private String userName;
}
