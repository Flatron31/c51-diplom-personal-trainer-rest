package com.example.c51diplompersonaltrainerrest.service;

import com.example.c51diplompersonaltrainerrest.configuration.security.Jwt.GenerateJwtUser;
import com.example.c51diplompersonaltrainerrest.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService service;

    public JwtUserDetailsService(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User with username: " + username + "not found");
        }

        return GenerateJwtUser.create(user);
    }
}
