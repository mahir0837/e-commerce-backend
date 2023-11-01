package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.UserDto;
import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerNewUser(user));
    }

    @GetMapping({"/userDetails/{userName}"})

    public User userDetails(@PathVariable("userName")String userName){
        return userService.getUserDetails(userName);
    }
    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
