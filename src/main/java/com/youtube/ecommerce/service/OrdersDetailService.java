package com.youtube.ecommerce.service;


import com.youtube.ecommerce.dto.OrderDetailDto;
import com.youtube.ecommerce.entity.OrderInput;

import java.util.List;

public interface OrdersDetailService {
    void placeOrder(OrderInput orderInput,boolean isSingleProductChekout);
    List<OrderDetailDto> getOrderDetails();
    List<OrderDetailDto>gettAllOrder(String status);
    OrderDetailDto markOrderAsDelivere(Long id, int addDays);

//    TransectionDetails createTransection(Double amount) throws RazorpayException;
    String createTransection(OrderDetailDto dto, String userName, double amount);

    OrderDetailDto markOrderAsProcessing(Long id, int addDays);

    OrderDetailDto markOrderAsQualityCheck(Long id, int addDays);
}
