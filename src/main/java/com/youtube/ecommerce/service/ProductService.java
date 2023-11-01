package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dto.ProductDto;
import com.youtube.ecommerce.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product addNewProduct(ProductDto product, MultipartFile[]file);
    List<Product>listAllProduct(int pageNumber, String serachKey, Long categoryId, Long productBrandId, int selectedSortValue);
    void deleteProduct(Long Id);

    Product getPRoductById(Long productId);
    List<Product> getProductDetails(boolean isSingleProductCheckout,Long productId);

    List<Product> listProducts(int pageNumber, String searchKey);

    List<Product> listAllProductBaseOnTheCategory(int pageNumber, String searchKey, Long categoryId);
}
