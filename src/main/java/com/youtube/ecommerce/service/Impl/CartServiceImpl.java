package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.dto.CartDto;
import com.youtube.ecommerce.dto.ProductDto;
import com.youtube.ecommerce.dto.UserDto;
import com.youtube.ecommerce.entity.Cart;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.CartService;
import com.youtube.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserDao userService;
    @Autowired
    private ProductService productService;

    @Autowired
    MapperUtil mapperUtil;

    @Override
    public Cart addToCart(Long productId) {
        String userName=JwtRequestFilter.CURRENT_USER;

        Product product=productService.getPRoductById(productId);

        User user=null;
        Cart cart=null;
        if (userName!=null){
            user=userService.findById(userName).get();
        }
        List<Cart>cartList=cartDao.findByUser(user);

        List<Cart>filterList=cartList.stream().filter(x->x.getProduct().getProductId()==productId)
                .collect(Collectors.toList());
        if (filterList.size()>0){
            return null;
        }
        if (product!=null){
        cart=new Cart(product,user);
        cartDao.save(mapperUtil.convert(cart,new Cart()));
        }
        return cart;
    }

    @Override
    public List<Cart> getCardDetailById() {
        String userName=JwtRequestFilter.CURRENT_USER;
        User user=userService.findById(userName).get();
        List <Cart>cartList=cartDao.findByUser(user);
        return cartList;
    }

    @Override
    public void deleteItem(Long id) {
    cartDao.deleteById(id);

    }
}
