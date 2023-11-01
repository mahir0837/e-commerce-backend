package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandDao extends JpaRepository<Brand,Long> {

    @Query("SELECT b FROM Brand b WHERE b.categories.id=:categoryId")
    List<Brand> getAllBrandWithCategoryId(@Param("categoryId") Long categoryId);

}
