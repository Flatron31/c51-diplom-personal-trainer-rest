package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.entity.Role;
import com.example.c51diplompersonaltrainerrest.entity.Status;
import com.example.c51diplompersonaltrainerrest.entity.User;
import com.example.c51diplompersonaltrainerrest.repository.RoleRepository;
import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User reg(User user) {
        Role roleUser = roleRepository.findByName("USER").get();
        List<Role> userRole = new ArrayList<>();
        userRole.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRole);
        user.setStatus(Status.ACTIVE);
        User regUser = userRepository.save(user);
        return regUser;
    }

}
