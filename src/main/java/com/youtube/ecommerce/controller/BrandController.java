package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.BrandDto;
import com.youtube.ecommerce.entity.Brand;
import com.youtube.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("/getAllBrand")
    public ResponseEntity<List<Brand>>brandList(@RequestParam(defaultValue = "0") Long categoryId){
        return ResponseEntity.ok(brandService.getAllBrand(categoryId));
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/addBrand")
    public ResponseEntity<Brand>addBrand(@RequestBody BrandDto dto){
        return ResponseEntity.ok(brandService.addNewBrand(dto));
    }
}
