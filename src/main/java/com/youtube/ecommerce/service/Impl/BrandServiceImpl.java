package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.dao.BrandDao;
import com.youtube.ecommerce.dao.CategoryDao;
import com.youtube.ecommerce.dto.BrandDto;
import com.youtube.ecommerce.dto.CategoryDto;
import com.youtube.ecommerce.entity.Brand;
import com.youtube.ecommerce.entity.Category;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.BrandService;
import com.youtube.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandDao brandDao;
    @Autowired
    CategoryService categoryService;
    @Autowired
    MapperUtil mapperUtil;
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Brand> getAllBrand(Long categoryId) {
        return brandDao.getAllBrandWithCategoryId(categoryId);
    }

    @Override
    public Brand addNewBrand(BrandDto dto) {
        Brand brand=new Brand();
        brand.setBrandName(dto.getBrandName());
        Category category=categoryDao.findById(dto.getCategoryId()).orElseThrow();
        brand.setCategories(category);
        brandDao.save(brand);
        return brand;
    }
}
