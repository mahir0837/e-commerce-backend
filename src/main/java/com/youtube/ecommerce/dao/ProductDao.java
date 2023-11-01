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
    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')) AND p.productCategory.id = :categoryId AND p.productBrand.id=:brandId")
    List<Product> findProductsWithKey(@Param("productName") String productName, @Param("productDescription") String productDescription, @Param("categoryId") Long categoryId,@Param("brandId") Long brandId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')) AND p.productCategory.id = :categoryId AND p.productBrand.id=:brandId order by p.productDiscountedPrice asc ")
    List<Product> sortByPriceLoverToHigh(@Param("productName") String productName, @Param("productDescription") String productDescription, @Param("categoryId") Long categoryId,@Param("brandId") Long brandId, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')) AND p.productCategory.id = :categoryId AND p.productBrand.id=:brandId order by p.productDiscountedPrice desc ")
    List<Product> sortByPriceLHighToLow(@Param("productName") String productName, @Param("productDescription") String productDescription, @Param("categoryId") Long categoryId,@Param("brandId") Long brandId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')) AND p.productCategory.id = :categoryId AND p.productBrand.id=:brandId order by p.productName asc ")
    List<Product> sortByProductAlpAtoZ(@Param("productName") String productName, @Param("productDescription") String productDescription, @Param("categoryId") Long categoryId,@Param("brandId") Long brandId, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')) AND p.productCategory.id = :categoryId AND p.productBrand.id=:brandId order by p.productName desc ")
    List<Product> sortByProductAlpZtoA(@Param("productName") String productName, @Param("productDescription") String productDescription, @Param("categoryId") Long categoryId,@Param("brandId") Long brandId, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%'))")
    List<Product> findProducts(@Param("productName") String productName, @Param("productDescription") String productDescription, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE CONCAT('%', LOWER(:productName), '%') OR " +
            "LOWER(p.productDescription) LIKE CONCAT('%', LOWER(:productDescription), '%')) AND p.productCategory.id = :categoryId")
    List<Product> findAllProductBaseOnTheCategory(@Param("productName") String productName, @Param("productDescription") String productDescription, @Param("categoryId") Long categoryId, Pageable pageable);
}
