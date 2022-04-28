package com.example.c51diplompersonaltrainerrest.configuration.security;

import com.example.c51diplompersonaltrainerrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    private final UserService userService;
//    public JwtUserDetailsService(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.findByUsername(username).isPresent()){
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        return null;
    }
}
