package com.youtube.ecommerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    @OneToOne
    private Product product;
    @OneToOne
    private User user;

    public Cart(Product product, User user) {
        this.product = product;
        this.user = user;
    }
}

