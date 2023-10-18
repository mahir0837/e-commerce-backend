package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dto.CategoryDto;
import com.youtube.ecommerce.entity.Category;
import java.util.List;

public interface CategoryService {

    Category addNewCategory(Category category);

    List<CategoryDto> getCategories();

    void deleteCategory(Long id);

    Category updateCategory(Category category);

    CategoryDto getCategoryById(Long id);


}
