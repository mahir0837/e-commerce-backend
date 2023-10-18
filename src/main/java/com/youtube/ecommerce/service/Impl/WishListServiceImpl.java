package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.dao.WishListDao;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.entity.WishList;
import com.youtube.ecommerce.service.ProductService;
import com.youtube.ecommerce.service.UserService;
import com.youtube.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    WishListDao wishListDao;
    @Autowired
    ProductService productService;
    @Autowired
    UserDao userDao;

    @Override
    public List<WishList> getAllWishListProduct() {
        String userName=JwtRequestFilter.CURRENT_USER;
        User user=userDao.findById(userName).get();
        return wishListDao.findByUser(user);
    }

    @Override
    public WishList addWishlist(Long productId) {
        String userName= JwtRequestFilter.CURRENT_USER;
        User user=userDao.findById(userName).get();
        Product product=productService.getPRoductById(productId);

        if (userName!=null&&product!=null){
            WishList wishList=new WishList();
            wishList.setUser(user);
            wishList.setProduct(product);
            return wishListDao.save(wishList);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long wishListId) {
    wishListDao.deleteById(wishListId);
    }
}
