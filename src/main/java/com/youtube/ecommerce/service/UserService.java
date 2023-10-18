package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dto.UserDto;
import com.youtube.ecommerce.entity.User;


public interface UserService {

    void initRoleAndUser();

    User registerNewUser(User user);
}
