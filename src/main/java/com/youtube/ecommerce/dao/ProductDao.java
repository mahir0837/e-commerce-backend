package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductDao extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p")
    List<Product>findAllProduct(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')")
    List<Product> findProductsWithKey(@Param("productName") String productName, @Param("productDescription") String productDescription, Pageable pageable);

}
