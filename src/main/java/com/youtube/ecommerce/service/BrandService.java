package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dto.BrandDto;
import com.youtube.ecommerce.entity.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrand(Long categoryId);

    Brand addNewBrand(BrandDto dto);
    
}
