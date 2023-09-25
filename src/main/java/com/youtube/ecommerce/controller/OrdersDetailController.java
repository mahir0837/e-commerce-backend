package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.OrderInput;
import com.youtube.ecommerce.service.OrdersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrdersDetailController {

    @Autowired
    private OrdersDetailService ordersDetailService;

    @PreAuthorize("hasRole('User')")
    @PostMapping("/placeOrder/{isSingleProductChekout}")
    public void placeOrder(@RequestBody OrderInput orderInput,
                           @PathVariable("isSingleProductChekout")boolean isSingleProductChekout){

       ordersDetailService.placeOrder(orderInput,isSingleProductChekout);
    }
    @PreAuthorize("hasRole('User')")
    @GetMapping("/getOrderDetails")
    public List<OrderDetail> getOrderDetails(){
        return ordersDetailService.getOrderDetails();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/getAllOrderDetails/{status}")
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status")String status){
        return ordersDetailService.gettAllOrder(status);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/markOrderAsDelivered/{orderId}")
    public OrderDetail markOrderAsDelivered(@PathVariable("orderId")Long id){
        return ordersDetailService.markOrderAsDelivere(id);
    }
}
