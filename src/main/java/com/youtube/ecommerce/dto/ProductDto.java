package com.youtube.ecommerce.dto;

import com.youtube.ecommerce.entity.Category;
import com.youtube.ecommerce.entity.ImageModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long productId;
    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;
    private Long productCategory;
    private Long productBrand;
    private Set<ImageModel> productImages;


}
