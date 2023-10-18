package com.youtube.ecommerce.dto;

import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CartDto {

    private Long cartId;
    private Product product;
    private User user;

    public CartDto(Product product, User user) {
        this.product = product;
        this.user = user;
    }
}
