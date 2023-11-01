package com.youtube.ecommerce.controller;


import com.youtube.ecommerce.dto.CategoryDto;
import com.youtube.ecommerce.dto.ProductDto;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> addNewProduct(@RequestPart("product") ProductDto productDto,
                                                 @RequestPart("imageFile") MultipartFile[] file) {
        return ResponseEntity.ok(productService.addNewProduct(productDto, file));
    }


    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue = "0") int pageNumber,
                                                       @RequestParam(defaultValue = "") String searchKey) {
        return ResponseEntity.ok(productService.listProducts(pageNumber, searchKey));
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(defaultValue = "0") int pageNumber,
                                                       @RequestParam(defaultValue = "") String searchKey,
                                                       @RequestParam(defaultValue = "0") Long categoryId,
                                                       @RequestParam(defaultValue = "0") Long productBrandId,
                                                       @RequestParam(defaultValue = "0") int selectedSortValue) {
        return ResponseEntity.ok(productService.listAllProduct(pageNumber, searchKey, categoryId,productBrandId,selectedSortValue));
    }
    @GetMapping("/getProductsInCategory")
    public ResponseEntity<List<Product>> getProductsInCategory(@RequestParam(defaultValue = "0") int pageNumber,
                                                       @RequestParam(defaultValue = "") String searchKey,
                                                       @RequestParam(defaultValue = "0") Long categoryId) {
        return ResponseEntity.ok(productService.listAllProductBaseOnTheCategory(pageNumber, searchKey, categoryId));
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({"/deleteProductDetails/{productId}"})
    public void deleteProductDetails(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/getProductDetailsById/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getPRoductById(productId));
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/getProductDetails/{isSingleProductChekout}/{productId}")
    public ResponseEntity<List<Product>> getProductDetails(@PathVariable(name = "isSingleProductChekout") boolean isSingleProductCheckout,
                                                           @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductDetails(isSingleProductCheckout, productId));
    }
}
