package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.converter.UserConverter;
import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.Role;
import com.example.c51diplompersonaltrainerrest.entity.Status;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.repository.RoleRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void registration(UserDTO userDTO) {
        User user = UserConverter.convertToUserFromUserSignupDTO(userDTO);
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("USER");
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

}
