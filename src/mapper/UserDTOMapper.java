package com.mycompany.irr00_group_project.mapper;

import com.mycompany.irr00_group_project.dto.UserDTO;
import com.mycompany.irr00_group_project.entity.User;

/**
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class UserDTOMapper {
    private UserDTOMapper() {
    }

    public static UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
