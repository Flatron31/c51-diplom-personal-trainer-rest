package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.converter.UserConverter;
import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.Program;
import com.example.c51diplompersonaltrainerrest.entity.Role;
import com.example.c51diplompersonaltrainerrest.entity.Status;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.exception.InvalidParametrException;
import com.example.c51diplompersonaltrainerrest.exception.NotFoundException;
import com.example.c51diplompersonaltrainerrest.mapper.UserMapper;
import com.example.c51diplompersonaltrainerrest.repository.RoleRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public void registration(UserDTO userDTO) {
        User user = UserConverter.convertToUserFromUserSignupDTO(userDTO);
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roles.add(role);
        user.setRoleList(roles);
        user.setStatus(Status.ACTIVE);
        role.setUser(user);
        userRepository.save(user);
        roleRepository.save(role);
    }

    public User findByUsername(String username) {
        User byUsername = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username: " + username + " not found"));
        return byUsername;
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void validateUserName(String username) {
        if (username == null | userRepository.findByUsername(username).isEmpty()) {
            throw new NotFoundException();
        }
    }

    public void validateUserId(long id) {
        if (id < 1 | userRepository.findById(id).isEmpty()) {
            throw new InvalidParametrException();
        }
    }

    public User updateUser(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username).get();
        List<Role> roleList = user.getRoleList();
        List<Program> programList = user.getProgramList();

        User updateUser = userMapper.userDTOToUser(userDTO);
        updateUser.setId(user.getId());
        updateUser.setUsername(username);
        updateUser.setRoleList(roleList);
        updateUser.setProgramList(programList);
        updateUser.setStatus(user.getStatus());
        userRepository.save(updateUser);

        log.info("User {} changed successfully", username);

        return updateUser;
    }
}
