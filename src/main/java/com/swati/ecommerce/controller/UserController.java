package com.swati.ecommerce.controller;

import com.swati.ecommerce.dao.UserDto;
import com.swati.ecommerce.helper.AppConstant;
import com.swati.ecommerce.payloads.ApiResponse;
import com.swati.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * @param userDto
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to save user
     */
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Request Entering for create single user");
        UserDto createUser = this.userService.createUser(userDto);
        logger.info("Complete Request for create  user ");
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to update user by userId
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        logger.info("Request Entering for update single user with userId :{}", userId);
        UserDto updateUser = this.userService.updateUser(userDto, userId);
        logger.info("Complete Request for update single user with userId :{}", userId);
        return new ResponseEntity<UserDto>(updateUser, HttpStatus.CREATED);
    }

    /**
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to get All user
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        logger.info("Request Entering for get All  user");
        List<UserDto> getAllUser = this.userService.getAllUser();
        logger.info("Complete Request for get All  user ");
        return new ResponseEntity<>(getAllUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to get user by userId
     */
    @GetMapping("userid/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Request Entering for get single user with userId :{}", userId);
        UserDto getById = this.userService.getUserById(userId);
        logger.info("Complete Request for get single user with userId :{}", userId);
        return new ResponseEntity<UserDto>(getById, HttpStatus.OK);
    }

    /**
     * @param userEmail
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to get user by userEmail
     */
    @GetMapping("/useremail/{userEmail}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String userEmail) {
        logger.info("Request Entering for get single user with userEmail :{}", userEmail);
        UserDto getByEmail = this.userService.getUserByEmail(userEmail);
        logger.info("Complete Request for get single user with userEmail :{}", userEmail);
        return new ResponseEntity<UserDto>(getByEmail, HttpStatus.OK);
    }

    /**
     * @param userId
     * @author Swati Nagare
     * @apiNote this is the api related to delete user by userId
     */
    @DeleteMapping("users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        logger.info("Request Entering for delete single user with userId :{}", userId);
        this.userService.deleteUser(userId);
        logger.info("Complete Request for delete single user with userId :{}", userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstant.USER_DELETE, true), HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to search user by keyword
     */
    @GetMapping("/posts/serch/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("Request Entering for search single user with keyword :{}", keyword);
        List<UserDto> list = this.userService.searchUser(keyword);
        logger.info("Complete Request for search single user with keyword :{}", keyword);
        return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);
    }

}
