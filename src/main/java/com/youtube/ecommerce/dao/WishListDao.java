package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListDao extends JpaRepository<WishList,Long> {
   List<WishList> findByUser(User user);
}
