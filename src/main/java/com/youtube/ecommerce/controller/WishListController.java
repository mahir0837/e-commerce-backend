package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.entity.WishList;
import com.youtube.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PreAuthorize("hasRole('User')")
    @GetMapping("/getAllWishList")
    public ResponseEntity<List<WishList>>getWishList(){
        return ResponseEntity.ok(wishListService.getAllWishListProduct());
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/addWishList")
    public ResponseEntity<WishList>addWishList(@RequestParam Long productId){
        return ResponseEntity.ok(wishListService.addWishlist(productId));
    }

    @PreAuthorize("hasRole('User')")
    @DeleteMapping ("/delete/{wishListId}")
    public void deleteItem(@PathVariable Long wishListId){
        wishListService.deleteProduct(wishListId);
    }
}
