package com.youtube.ecommerce.dto;

import com.youtube.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {

    private String brandName;
    private Long categoryId;

}
