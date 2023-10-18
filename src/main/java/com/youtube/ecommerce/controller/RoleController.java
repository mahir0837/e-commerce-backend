package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.RoleDto;
import com.youtube.ecommerce.entity.Role;
import com.youtube.ecommerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping({"/createNewRole"})
    public ResponseEntity<RoleDto> createNewRole(@RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleService.createNewRole(dto));
    }
}
