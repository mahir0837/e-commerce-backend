package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.OrdersDetailServiceDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.entity.*;
import com.youtube.ecommerce.service.CartService;
import com.youtube.ecommerce.service.OrdersDetailService;
import com.youtube.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersDetailServiceImpl implements OrdersDetailService {
    @Autowired
    private OrdersDetailServiceDao ordersDetailServiceDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDao userService;
    @Autowired
    private CartService cartService;

    private static final String ORDER_PLACE = "Placed";
        private static final String ORDER_DELIVERED = "Delivered";
    @Autowired
    private CartDao cartDao;

    @Override
    public void placeOrder(OrderInput orderInput, boolean isSingleProductChekout) {

        List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantityList();
        for (OrderProductQuantity o : orderProductQuantityList) {
            Product product = productService.getPRoductById(o.getProductId());
            String currentUserName = JwtRequestFilter.CURRENT_USER;
            User user = userService.findById(currentUserName).get();
            OrderDetail order = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACE,
                    calculateOrderAmount(o) * 1.13,
                    product,
                    user
            );
            if (!isSingleProductChekout) {
                List<Cart> carts = cartService.getCardDetailById();
                carts.forEach(p-> cartDao.deleteById(p.getCartId()));
            }
            ordersDetailServiceDao.save(order);
        }

    }

    @Override
    public List<OrderDetail> getOrderDetails() {
        String userName=JwtRequestFilter.CURRENT_USER;
        User user=userService.findById(userName).get();
        return ordersDetailServiceDao.findByUser(user);
    }

    @Override
    public List<OrderDetail> gettAllOrder(String status) {
        List<OrderDetail>orderDetailList=new ArrayList<>();

        if (status.equals("All")){
            ordersDetailServiceDao.findAll().forEach(
                    x->orderDetailList.add(x)
            );
            return orderDetailList;
        } else {
            ordersDetailServiceDao.findByOrderStatus(status).forEach(
                    x->orderDetailList.add(x)
            );
            return orderDetailList;
        }

    }

    @Override
    public OrderDetail markOrderAsDelivere(Long id) {
        OrderDetail orderDetail=ordersDetailServiceDao.findById(id).get();
        orderDetail.setOrderStatus(ORDER_DELIVERED);
        return ordersDetailServiceDao.save(orderDetail);
    }

    private Double calculateOrderAmount(OrderProductQuantity o) {
        Product product = productService.getPRoductById(o.getProductId());
        return product.getProductDiscountedPrice() * o.getQuantity();
    }


}
