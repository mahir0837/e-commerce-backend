package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.entity.Cart;
import com.youtube.ecommerce.entity.ImageModel;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.service.CartService;
import com.youtube.ecommerce.service.ProductService;
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

    @Override
    public Product addNewProduct(Product product, MultipartFile[] file) {
        try {
            Set<ImageModel> images = uploadImage(file);
            product.setProductImages(images);
            return productDao.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> listAllProduct(int pageNumber, String serachKey) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        if (serachKey.equals("")) {
            return productDao.findAllProduct(pageable);
        } else {
            return productDao.findProductsWithKey(
                    serachKey, serachKey, pageable);
        }

    }

    @Override
    public Product updateProduct(Product product, MultipartFile[] file) {
        return null;
    }

    @Override
    public void deleteProduct(Long Id) {
        Product product = productDao.findById(Id).get();
        productDao.delete(product);
    }

    @Override
    public Product getPRoductById(Long productId) {
        return productDao.findById(productId).get();
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
            return carts.stream().map(Cart::getProduct).collect(Collectors.toList());

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
