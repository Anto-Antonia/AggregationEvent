package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.request.role.AddRoleRequest;
import com.example.aggregationevent.dto.request.user.AddUserRequest;
import com.example.aggregationevent.dto.response.events.EventsResponse;
import com.example.aggregationevent.dto.response.role.RoleResponse;
import com.example.aggregationevent.dto.response.user.UserResponse;
import com.example.aggregationevent.entity.Event;
import com.example.aggregationevent.entity.Role;
import com.example.aggregationevent.entity.User;
import com.example.aggregationevent.exceptions.events.EventsNotFoundException;
import com.example.aggregationevent.exceptions.role.RoleNotFoundException;
import com.example.aggregationevent.exceptions.user.UserNotFoundException;
import com.example.aggregationevent.mapper.UserRoleMapper;
import com.example.aggregationevent.repository.RoleRepository;
import com.example.aggregationevent.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleMapper userRoleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Role addRole(AddRoleRequest addRoleRequest) {
        Role role = userRoleMapper.fromAddRoleRequest(addRoleRequest);
       return roleRepository.save(role);
    }

    @Override
    public User addUser(AddUserRequest addUserRequest) {
        User user = userRoleMapper.fromAddUserRequest(addUserRequest);

        List<String> userRoles = addUserRequest.getRolesName();
        List<Role> roles = roleRepository.findAll().stream().filter(element -> userRoles.contains(element.getName())).collect(Collectors.toList());

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponseList = users.stream()
                .map(element -> userRoleMapper.fromUserEntity(element)).collect(Collectors.toList());
        return userResponseList;

    }

    @Override
    public UserResponse getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {

            User user = userOptional.get();
            UserResponse userResponse = userRoleMapper.fromUserEntity(user);
            return userResponse;
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public RoleResponse getRoleById(Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if(roleOptional.isPresent()){

            Role role = roleOptional.get();
            RoleResponse roleResponse = userRoleMapper.fromRoleEntity(role);

            return roleResponse;
        } else {
            throw new RoleNotFoundException("Role not found");
        }
    }
}
