package com.youtube.ecommerce.service;


import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.OrderInput;
import java.util.List;

public interface OrdersDetailService {
    void placeOrder(OrderInput orderInput,boolean isSingleProductChekout);
    List<OrderDetail> getOrderDetails();
    List<OrderDetail>gettAllOrder();
}
