package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.CategoryDto;
import com.youtube.ecommerce.entity.Category;
import com.youtube.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/addNewCategory")
    public Category addNewCategory(@RequestBody Category category){
    return categoryService.addNewCategory(category);
    }

    @GetMapping("/getAllCategories")
    public List<CategoryDto> getCategoryList(){
        return categoryService.getCategories();
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteCategory/{categoryId}")
    public void delete(@PathVariable("categoryId")Long id){
        categoryService.deleteCategory(id);
    }
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/updateCategory")
    public Category updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/getCategoryById/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable("categoryId") Long id){
        return categoryService.getCategoryById(id);
    }
}
