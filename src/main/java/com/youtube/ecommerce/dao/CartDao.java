package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Cart;
import com.youtube.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends JpaRepository<Cart,Long> {
    List<Cart>findByUser(User user);
}
