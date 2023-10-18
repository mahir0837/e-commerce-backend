package com.youtube.ecommerce.entity;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderInput {

    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String alternateContactNumber;
    private String transectionId;
    private List<OrderProductQuantity>orderProductQuantityList;
}