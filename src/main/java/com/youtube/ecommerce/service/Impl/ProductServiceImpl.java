package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.dao.BrandDao;
import com.youtube.ecommerce.dao.CategoryDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dto.ProductDto;
import com.youtube.ecommerce.entity.*;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.CartService;
import com.youtube.ecommerce.service.CategoryService;
import com.youtube.ecommerce.service.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CartService cartService;
    @Autowired
    MapperUtil mapperUtil;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BrandDao brandDao;

    @Override
    public Product addNewProduct(ProductDto productDto, MultipartFile[] file) {
        Product product=new Product();
        try {
            Set<ImageModel> images = uploadImage(file);
            product.setProductImages(images);
            product.setProductId(productDto.getProductId());
            product.setProductName(productDto.getProductName());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductDiscountedPrice(productDto.getProductDiscountedPrice());
            product.setProductActualPrice(productDto.getProductActualPrice());
            Category category=categoryDao.findById(productDto.getProductCategory()).orElseThrow(()->new NotFoundException("Category Not Found"));
            Brand brand=brandDao.findById(productDto.getProductBrand()).orElseThrow(()->new NotFoundException("Brand Not Found"));
            product.setProductCategory(category);
            product.setProductBrand(brand);
            return  productDao.save(product);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> listAllProduct(int pageNumber, String searchKey, Long categoryId,
                                        Long productBrandId, int selectedSortValue) {
        Pageable pageable = PageRequest.of(pageNumber, 4);

        if (searchKey.equals("")&& categoryId==0&&productBrandId==0) {
            List<Product>productList= productDao.findAllProduct(pageable);
            return productList;
        } else {
            if (selectedSortValue==1){
                List<Product>productList= productDao.sortByPriceLoverToHigh(searchKey, searchKey,
                        categoryId,productBrandId,pageable);
                return productList;
            } else if (selectedSortValue==2) {
                List<Product>productList= productDao.sortByPriceLHighToLow(searchKey, searchKey,
                        categoryId,productBrandId,pageable);
                return productList;
            } else if (selectedSortValue==3) {
                List<Product>productList= productDao.sortByProductAlpAtoZ(searchKey, searchKey,
                        categoryId,productBrandId,pageable);
                return productList;
            } else if (selectedSortValue==4) {
                List<Product>productList= productDao.sortByProductAlpZtoA(searchKey, searchKey,
                        categoryId,productBrandId,pageable);
                return productList;
            }
            List<Product>productList= productDao.findProductsWithKey(searchKey, searchKey,
                    categoryId,productBrandId,pageable);
            return productList;
        }

    }


    @Override
    public void deleteProduct(Long Id) {
        Product product = productDao.findById(Id).get();
        productDao.delete(product);
    }

    @Override
    public Product getPRoductById(Long productId) {
        Product product=productDao.findById(productId).get();
        return product;
    }

    @Override
    public List<Product> getProductDetails(boolean isSingleProductCheckout, Long productId) {
        if (isSingleProductCheckout && productId != 0) {
            List<Product> productList = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            productList.add(product);
            return productList;
        } else {
            List<Cart>carts=cartService.getCardDetailById();
            List<Product> productList =carts.stream().map(Cart::getProduct).collect(Collectors.toList());
           return productList;

        }

    }

    @Override
    public List<Product> listProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 4);

        if (searchKey.equals("")) {
            List<Product>productList= productDao.findAllProduct(pageable);
            return productList;
        } else {
            List<Product>productList= productDao.findProducts(searchKey, searchKey,pageable);
            return productList;
        }
    }

    @Override
    public List<Product> listAllProductBaseOnTheCategory(int pageNumber, String searchKey, Long categoryId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        if (searchKey.equals("")) {
            List<Product>productList= productDao.findAllProduct(pageable);
            return productList;
        } else {
            List<Product>productList= productDao.findAllProductBaseOnTheCategory(searchKey, searchKey,categoryId,pageable);
            return productList;
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }
}
