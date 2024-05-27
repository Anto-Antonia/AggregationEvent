package com.example.aggregationevent.controller;

import com.example.aggregationevent.dto.request.role.AddRoleRequest;
import com.example.aggregationevent.dto.request.user.AddUserRequest;
import com.example.aggregationevent.dto.response.role.RoleResponse;
import com.example.aggregationevent.dto.response.user.UserResponse;
import com.example.aggregationevent.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/role")
    public ResponseEntity<Void> addRole(@RequestBody AddRoleRequest addRoleRequest){
        userRoleService.addRole(addRoleRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<Void> addUser(@RequestBody AddUserRequest addUserRequest){
        userRoleService.addUser(addUserRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@RequestParam Integer id){

        userRoleService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userRoleService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id){
        UserResponse userResponse = userRoleService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Integer id){
        RoleResponse roleResponse = userRoleService.getRoleById(id);

        return ResponseEntity.status(HttpStatus.OK).body(roleResponse);
    }
}
