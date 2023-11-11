package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.dao.CategoryDao;
import com.youtube.ecommerce.dto.CategoryDto;
import com.youtube.ecommerce.entity.Category;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryDao categoryDao;
    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryDao categoryDao, MapperUtil mapperUtil) {
        this.categoryDao = categoryDao;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Category addNewCategory(Category category) {

        return categoryDao.save(category);
    }

    @Override
    public List<CategoryDto> getCategories() {

        return categoryDao.findAll().stream().
                map(c->mapperUtil.convert(c,new CategoryDto())).
                collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryDao.findById(id).get();
        categoryDao.delete(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category updateCategory = categoryDao.findById(category.getId()).get();
        updateCategory.setCategoryName(category.getCategoryName());

        categoryDao.save(updateCategory);
        return updateCategory;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return mapperUtil.convert(categoryDao.findById(id).get(),new CategoryDto());
    }
}
