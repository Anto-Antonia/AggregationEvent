package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.request.role.AddRoleRequest;
import com.example.aggregationevent.dto.request.user.AddUserRequest;
import com.example.aggregationevent.dto.response.role.RoleResponse;
import com.example.aggregationevent.dto.response.user.UserResponse;
import com.example.aggregationevent.entity.Role;
import com.example.aggregationevent.entity.User;
import com.example.aggregationevent.repository.UserRepository;

import java.util.List;


public interface UserRoleService {

    public Role addRole(AddRoleRequest addRoleRequest);

    public User addUser(AddUserRequest addUserRequest);
    void deleteUser(Integer id);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Integer id);
    RoleResponse getRoleById(Integer id);
}
