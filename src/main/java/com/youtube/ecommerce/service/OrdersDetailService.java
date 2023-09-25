package com.youtube.ecommerce.service;


import com.razorpay.RazorpayException;
import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.OrderInput;
import com.youtube.ecommerce.entity.TransectionDetails;

import java.util.List;

public interface OrdersDetailService {
    void placeOrder(OrderInput orderInput,boolean isSingleProductChekout);
    List<OrderDetail> getOrderDetails();
    List<OrderDetail>gettAllOrder(String status);
    OrderDetail markOrderAsDelivere(Long id);
    TransectionDetails createTransection(Double amount) throws RazorpayException;
}
