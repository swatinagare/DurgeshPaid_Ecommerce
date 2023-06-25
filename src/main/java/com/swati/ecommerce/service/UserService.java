package com.swati.ecommerce.service;

import com.swati.ecommerce.dao.UserDto;
import com.swati.ecommerce.payloads.PageableResponse;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, String userId);
    PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    UserDto getUserById(String userId);
    UserDto getUserByEmail(String userEmail);
    void deleteUser(String userId);
    List<UserDto> searchUser(String keyword);
}
