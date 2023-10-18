package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dto.CartDto;
import com.youtube.ecommerce.entity.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Long productId);

    List<Cart> getCardDetailById();


    void deleteItem(Long id);
}
