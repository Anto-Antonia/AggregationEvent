package com.example.aggregationevent.service;

import com.example.aggregationevent.dto.request.role.AddRoleRequest;
import com.example.aggregationevent.dto.request.user.AddUserRequest;
import com.example.aggregationevent.dto.response.role.RoleResponse;
import com.example.aggregationevent.dto.response.user.UserResponse;
import com.example.aggregationevent.entity.Event;
import com.example.aggregationevent.entity.Role;
import com.example.aggregationevent.entity.User;
import com.example.aggregationevent.mapper.UserRoleMapper;
import com.example.aggregationevent.repository.RoleRepository;
import com.example.aggregationevent.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {

    @Mock       //Am folosit Mock in loc de MockBean pe repository
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks    //InjectMocks pentru ServiceImpl
    private UserRoleServiceImpl userRoleServiceImpl;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Test
    public void addUser_whenSuccessful_returnUser(){
        Role role = new Role();
        role.setName("USER");

        Event event = new Event();
        event.setTitle("Octoberfest");

        //GIVEN
        AddUserRequest addUserRequest = new AddUserRequest("Alina", "1234", "alina@gmail.com", List.of("USER"));

        User userToSave = new User();
        userToSave.setUsername("Alina");
        userToSave.setPassword("1234");
        userToSave.setEmail("alina@gmail.com");
        userToSave.setRoles(List.of(role));

        User userSaved = new User(1, "Alina", "1234", "alina@gmail.com", List.of(role), List.of(event));

        when(userRoleMapper.fromAddUserRequest(any(AddUserRequest.class))).thenReturn(userToSave);
        when(userRepository.save(any())).thenReturn(userSaved);

        //WHEN
        User userAdded = userRoleServiceImpl.addUser(addUserRequest);

        //THEN
        assertEquals(userSaved.getId(), userAdded.getId());
        assertEquals(userSaved.getUsername(), userAdded.getUsername());
        assertEquals(userSaved.getEmail(), userAdded.getEmail());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void addRole_whenSuccessful_ReturnRole(){
        User user = new User();
        user.setId(1);

        //GIVEN
        AddRoleRequest addRoleRequest = new AddRoleRequest("USER");

        Role roleToSave = new Role();
        roleToSave.setName("Alina");

        Role roleSaved = new Role(1, "Alina", List.of(user));

        when(userRoleMapper.fromAddRoleRequest(any(AddRoleRequest.class))).thenReturn(roleToSave);
        when(roleRepository.save(any())).thenReturn(roleSaved);

        //WHEN
        Role roleAdded = userRoleServiceImpl.addRole(addRoleRequest);

        //THEN
        assertEquals(roleSaved.getName(), roleAdded.getName());
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    public void deleteUserById_WhenUserExists_thenVerifyDelete(){
        Integer userId = 1;

        userRoleServiceImpl.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void getAllUsers_thenReturnAllUsers(){
        Role role = new Role();
        role.setName("GUEST");

        Event event = new Event();
        event.setTitle("Painting at dawn");

       User user1 = new User(1, "Alex", "5342", "alex@gmail.com", List.of(role), List.of(event));
       User user2 = new User(2, "Raluca", "2345", "raluca@gmail.com", List.of(role), List.of(event));

       List<User> users = List.of(user1, user2);

        UserResponse userResponse = new UserResponse("Alex", "alex@gmail.com");
        UserResponse userResponse2 = new UserResponse("Raluca", "raluca@gmail.com");

        when(userRepository.findAll()).thenReturn(users);
        when(userRoleMapper.fromUserEntity(user1)).thenReturn(userResponse);
        when(userRoleMapper.fromUserEntity(user2)).thenReturn(userResponse2);

        List<UserResponse> userResponseList = userRoleServiceImpl.getAllUsers();

        assertEquals(2, userResponseList.size());
        assertEquals("Alex", userResponseList.get(0).getUsername());
        assertEquals("alex@gmail.com", userResponseList.get(0).getEmail());

        assertEquals("Raluca", userResponseList.get(1).getUsername());
        assertEquals("raluca@gmail.com", userResponseList.get(1).getEmail());

        verify(userRepository, times(1)).findAll();
        verify(userRoleMapper, times(1)).fromUserEntity(user1);
        verify(userRoleMapper, times(1)).fromUserEntity(user2);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,4,6,8,10})
    public void getUserById_whenUserExists_thenReturnUser(Integer id){
        Role role = new Role();
        role.setName("GUEST");

        Event event = new Event();
        event.setTitle("Painting at dawn");

        User user = new User(id, "Ana", "1234", "ana@gmail.com", List.of(role),List.of(event));
        UserResponse userResponse = new UserResponse("Ana", "ana@gmail.com");


        when(userRepository.findById(eq(id))).thenReturn(Optional.of(user));
        when(userRoleMapper.fromUserEntity(user)).thenReturn(userResponse);

       UserResponse userFound = userRoleServiceImpl.getUserById(id);

        assertEquals(user.getUsername(), userFound.getUsername());
        assertEquals(user.getEmail(), userFound.getEmail());

    }

    @Test
    public void getUserById_whenDoesntExist_throwRuntimeException(){

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, ()-> userRoleServiceImpl.getUserById(any()));
        assertEquals("User not found", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,4,6,8,10})
    public void getRoleById_whenRoleExists_thenReturnRole(Integer id){
        Role role = new Role();
        role.setName("USER");
        role.setId(id);

        RoleResponse roleResponse = new RoleResponse("USER");

        when(roleRepository.findById(eq(id))).thenReturn(Optional.of(role));
        when(userRoleMapper.fromRoleEntity(role)).thenReturn(roleResponse);

        RoleResponse roleFound = userRoleServiceImpl.getRoleById(id);

        assertEquals(role.getName(), roleFound.getName());
    }

    @Test
    public void getRoleById_whenRoleDoesntExist_throwRuntimeException(){
        when(roleRepository.findById(any())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, ()-> userRoleServiceImpl.getRoleById(any()));
        assertEquals("Role not found", ex.getMessage());

    }
}
