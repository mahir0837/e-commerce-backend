package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.CartDto;
import com.youtube.ecommerce.entity.Cart;
import com.youtube.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('User')")
    @GetMapping("/addToCart/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable("productId")Long productId){

        return ResponseEntity.ok(cartService.addToCart(productId));
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/getCardDetails")
    public ResponseEntity<List<Cart>> getCartDetails(){
        return ResponseEntity.ok(cartService.getCardDetailById());
    }

    @PreAuthorize("hasRole('User')")
    @DeleteMapping("/deleteCartItem/{cartId}")
    public void deleteCartItem(@PathVariable("cartId")Long id){
        cartService.deleteItem(id);
    }
}
