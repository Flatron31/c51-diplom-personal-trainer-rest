package com.example.c51diplompersonaltrainerrest.Mapper;

import com.example.c51diplompersonaltrainerrest.dto.UserDTO;
import com.example.c51diplompersonaltrainerrest.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO (User user);
    User userDTOToUser (UserDTO userDTO);
}
