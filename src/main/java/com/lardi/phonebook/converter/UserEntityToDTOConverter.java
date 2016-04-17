package com.lardi.phonebook.converter;

import com.lardi.phonebook.dto.UserDTO;
import com.lardi.phonebook.entity.User;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Nikolay Yashchenko
 */
public class UserEntityToDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        return userDTO;
    }
}
