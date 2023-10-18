package com.youtube.ecommerce.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.ecommerce.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String categoryName;



}
