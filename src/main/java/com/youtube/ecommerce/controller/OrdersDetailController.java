package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.OrderDetailDto;
import com.youtube.ecommerce.entity.OrderInput;
import com.youtube.ecommerce.service.OrdersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @GetMapping("/markOrderAsDelivered/{orderId}/{addDays}")
    public ResponseEntity<OrderDetailDto> markOrderAsDelivered(@PathVariable("orderId")Long id
            ,@PathVariable("addDays")int addDays){
        return ResponseEntity.ok(ordersDetailService.markOrderAsDelivere(id,addDays));
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/markOrderAsProcessing/{orderId}/{addDays}")
    public ResponseEntity<OrderDetailDto> markOrderAsProcessing(@PathVariable("orderId")Long id
            ,@PathVariable("addDays")int addDays){
        return ResponseEntity.ok(ordersDetailService.markOrderAsProcessing(id,addDays));
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/markOrderAsQualityCheck/{orderId}/{addDays}")
    public ResponseEntity<OrderDetailDto> markOrderAsQualityCheck(@PathVariable("orderId")Long id
            ,@PathVariable("addDays")int addDays){
        return ResponseEntity.ok(ordersDetailService.markOrderAsQualityCheck(id,addDays));
    }

    @PreAuthorize("hasRole('User')")
    @PostMapping("/createTransection/{userName}/{amount}")
    public ResponseEntity<String>createTransection(@RequestBody OrderDetailDto orderDetailDto,
                                                   @PathVariable (name = "userName") String userName,
                                                   @PathVariable (name = "amount") double amount){
        String chargeId = ordersDetailService.createTransection(orderDetailDto,userName,amount);
        if (chargeId!=null){
            return ResponseEntity.ok("Payment successful. Charge ID: " + chargeId);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
        }
    }
}
