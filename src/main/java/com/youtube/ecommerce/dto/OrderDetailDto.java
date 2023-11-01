package com.youtube.ecommerce.dto;

import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private Long orderId;
    private String orderFullName;
    private String orderFullAddress;
    private String orderContactNumber;
    private String orderAlternateContactNumber;
    private String orderStatus;
    private Double orderAmount;
    private LocalDate orderDate;
    private String trackingNumber;
    private Product product;
    private User user;
    private String paymentMethod;


    public OrderDetailDto(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

