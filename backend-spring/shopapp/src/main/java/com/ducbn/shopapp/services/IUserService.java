package com.ducbn.shopapp.services;

import com.ducbn.shopapp.dtos.UserDTO;
import com.ducbn.shopapp.exceptions.DataNotFoundException;
import com.ducbn.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
