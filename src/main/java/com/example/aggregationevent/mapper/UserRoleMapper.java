package com.example.aggregationevent.mapper;

import com.example.aggregationevent.dto.request.role.AddRoleRequest;
import com.example.aggregationevent.dto.request.user.AddUserRequest;
import com.example.aggregationevent.dto.response.role.RoleResponse;
import com.example.aggregationevent.dto.response.user.SignInResponse;
import com.example.aggregationevent.dto.response.user.UserResponse;
import com.example.aggregationevent.entity.Role;
import com.example.aggregationevent.entity.User;
import com.example.aggregationevent.exceptions.user.UserNotFoundException;
import com.example.aggregationevent.service.security.UserDetailsImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleMapper {

    public Role fromAddRoleRequest(AddRoleRequest addRoleRequest){
        Role role = new Role();
        role.setName(addRoleRequest.getName());
        role.setUsers(new ArrayList<>());

        return role;
    }

    public User fromAddUserRequest(AddUserRequest addUserRequest){
        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setPassword(addUserRequest.getPassword());
        user.setEmail(addUserRequest.getEmail());

        return user;
    }

    public UserResponse fromUserEntity(User user){
        UserResponse response = new UserResponse();

        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }

    public RoleResponse fromRoleEntity(Role role){
        RoleResponse response = new RoleResponse();

        response.setName(role.getName());

        return response;

    }

    public static SignInResponse fromUserDetailsImpl(UserDetailsImpl userDetails)
    {
        SignInResponse response = new SignInResponse();

        response.setName(userDetails.getUsername());
        response.setEmail(userDetails.getEmail());
        List<String> roles = userDetails.getAuthorities().stream().map(a->a.getAuthority()).toList();
        response.setRoles(roles);

        return response;
    }
}
