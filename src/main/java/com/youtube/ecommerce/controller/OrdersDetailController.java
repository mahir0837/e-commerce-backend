package com.youtube.ecommerce.controller;

import com.razorpay.RazorpayException;
import com.youtube.ecommerce.dto.OrderDetailDto;
import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.OrderInput;
import com.youtube.ecommerce.entity.TransectionDetails;
import com.youtube.ecommerce.service.OrdersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<OrderDetailDto> getOrderDetails(){
        return ordersDetailService.getOrderDetails();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/getAllOrderDetails/{status}")
    public ResponseEntity<List<OrderDetailDto>> getAllOrderDetails(@PathVariable(name = "status")String status){
        return ResponseEntity.ok(ordersDetailService.gettAllOrder(status));
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/markOrderAsDelivered/{orderId}")
    public ResponseEntity<OrderDetailDto> markOrderAsDelivered(@PathVariable("orderId")Long id){
        return ResponseEntity.ok(ordersDetailService.markOrderAsDelivere(id));
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/createTransection/{amount}")
    public TransectionDetails createTransection(@PathVariable(name = "amount")Double amount) throws RazorpayException {

        return ordersDetailService.createTransection(amount);
    }
}
