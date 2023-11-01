package com.youtube.ecommerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long OrderId;
    private String orderFullName;
    private String orderFullAddress;
    private String orderContactNumber;
    private String orderAlternateContactNumber;
    private String orderStatus;
    private Double orderAmount;
    private LocalDate orderDate;
    private String trackingNumber;
    @OneToOne
    private Product product;
    @OneToOne
    private User user;
    private String transectionId;

    public OrderDetail(String orderFullName, String orderFullAddress, String orderContactNumber,
                       String orderAlternateContactNumber, String orderStatus,
                       Double orderAmount, LocalDate orderDate, String trackingNumber,
                       Product product, User user, String transectionId) {
        this.orderFullName = orderFullName;
        this.orderFullAddress = orderFullAddress;
        this.orderContactNumber = orderContactNumber;
        this.orderAlternateContactNumber = orderAlternateContactNumber;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.trackingNumber = trackingNumber;
        this.product = product;
        this.user = user;
        this.transectionId = transectionId;
    }

}
