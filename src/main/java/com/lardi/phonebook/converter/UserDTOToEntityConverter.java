package com.lardi.phonebook.converter;

import com.lardi.phonebook.dto.UserDTO;
import com.lardi.phonebook.entity.User;
import com.lardi.phonebook.service.UserService;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Nikolay Yashchenko
 */
public class UserDTOToEntityConverter implements Converter<UserDTO, User> {

    private UserService userService;

    public UserDTOToEntityConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User convert(UserDTO userDTO) {
        if (userDTO == null) return null;

        User user = null;

        if (userDTO.getId() != null) {
            user = userService.read(userDTO.getId());
        }

        if (user == null) {
            user = new User();
        }

        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());

        return user;
    }
}
