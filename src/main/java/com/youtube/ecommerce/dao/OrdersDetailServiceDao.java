package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdersDetailServiceDao extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByUser(User user);

    List<OrderDetail> findByOrderStatus(String status);
}
