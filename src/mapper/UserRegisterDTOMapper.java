package com.mycompany.irr00_group_project.mapper;

import com.mycompany.irr00_group_project.dto.UserRegisterDTO;
import com.mycompany.irr00_group_project.entity.User;

/**
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class UserRegisterDTOMapper {
    private UserRegisterDTOMapper() {
    }

    public static User mapToUserEntity(UserRegisterDTO userRegisterDTO) {
        return new User(userRegisterDTO.getName(), userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
    }
}
