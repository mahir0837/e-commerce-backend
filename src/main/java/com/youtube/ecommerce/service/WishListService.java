package com.youtube.ecommerce.service;

import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.WishList;

import java.util.List;


public interface WishListService {
    List<WishList> getAllWishListProduct();

    WishList addWishlist(Long productId);

    void deleteProduct(Long wishListId);
}
