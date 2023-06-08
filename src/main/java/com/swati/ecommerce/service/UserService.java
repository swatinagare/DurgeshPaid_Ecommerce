package com.swati.ecommerce.service;

import com.swati.ecommerce.dao.UserDto;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, String userId);
    List<UserDto> getAllUser();
    UserDto getUserById(String userId);
    UserDto getUserByEmail(String userEmail);
    void deleteUser(String userId);
    List<UserDto> searchUser(String keyword);
}
