package org.example.dao;

import org.example.dto.UserAuthDTO;
import org.example.dto.UserCreateDTO;

public interface UserDAO {
    boolean authenticateUser(UserAuthDTO userAuthDTO);
    void createUser(UserCreateDTO user);
}
