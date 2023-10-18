package com.youtube.ecommerce.dto;

import com.youtube.ecommerce.entity.Role;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor


public class UserDto {

    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private Set<Role> role;
}
