package com.youtube.ecommerce.controller;


import com.youtube.ecommerce.entity.ImageModel;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.ResponseWrapper;
import com.youtube.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseWrapper> addNewProduct(@RequestPart("product") Product product,
                                                         @RequestPart("imageFile") MultipartFile[] file) {
        return ResponseEntity.ok(new ResponseWrapper(
                "Product successfully added",
                productService.addNewProduct(product, file), HttpStatus.OK
        ));
    }


    @GetMapping("/getAllProducts")
    public List<Product> getAllProduct(@RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "")String searchKey) {
        return productService.listAllProduct(pageNumber,searchKey);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({"/deleteProductDetails/{productId}"})
    public void deleteProductDetails(@PathVariable("productId") Long productId ) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/getProductDetailsById/{productId}")
    public Product getProductById(@PathVariable("productId") Long productId) {
        return productService.getPRoductById(productId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/getProductDetails/{isSingleProductChekout}/{productId}")
    public List<Product> getProductDetails(@PathVariable(name = "isSingleProductChekout") boolean isSingleProductCheckout,
                                           @PathVariable("productId") Long productId) {
        return productService.getProductDetails(isSingleProductCheckout, productId);
    }
}
