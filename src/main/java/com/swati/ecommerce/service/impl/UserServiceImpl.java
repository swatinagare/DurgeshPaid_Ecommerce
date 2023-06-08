package com.swati.ecommerce.service.impl;

import com.swati.ecommerce.dao.UserDto;
import com.swati.ecommerce.entity.User;
import com.swati.ecommerce.exception.ResourceNotFoundException;
import com.swati.ecommerce.helper.AppConstant;
import com.swati.ecommerce.repository.UserRepo;
import com.swati.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Component
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param userDto
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to save user
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initiating dao call for the create the User");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = this.modelMapper.map(userDto, User.class);
        User saveUser = this.userRepo.save(user);
        logger.info("Completed  dao call for the create the User ");
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    /**
     * @param userId
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to update user by userId
     */
    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating dao call for the update the User with userId :{}", userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException(AppConstant.NOT_FOUND));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());
        User updatedUser = this.userRepo.save(user);
        logger.info("Completed  dao call for the update the User with userId:{}", userId);
        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    /**
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to get All user
     */
    @Override
    public List<UserDto> getAllUser() {
        logger.info("Initiating dao call for the get All the User");
        List<User> users = this.userRepo.findAll();
        List<UserDto> list = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        logger.info("Completed  dao call for the get All the User ");
        return list;
    }

    /**
     * @param userId
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to get user by userId
     */
    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating dao call for the get the User with userId :{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user","userId",userId));
        logger.info("Completed  dao call for the get the User with userId:{}", userId);
        return this.modelMapper.map(user, UserDto.class);
    }

    /**
     * @param userEmail
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to update user by userEmail
     */
    @Override
    public UserDto getUserByEmail(String userEmail) {
        logger.info("Initiating dao call for the get the User with userEmail :{}", userEmail);
        User user = this.userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException(AppConstant.NOT_FIND_EMAIL));
        logger.info("Completed  dao call for the get the User with userEmail:{}", userEmail);
        return this.modelMapper.map(user, UserDto.class);
    }

    /**
     * @param userId
     * @author Swati Nagare
     * @implNote  this is the impl related to delete user by userId
     */
    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating dao call for the delete the User with userId :{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new RuntimeException(AppConstant.NOT_FOUND));
        this.userRepo.delete(user);
        logger.info("Completed  dao call for the delete the User with userId:{}", userId);
    }

    /**
     * @param keyword
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to search user by keyword
     */
    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating dao call for the search the User with keyword :{}", keyword);
        List<User> users = this.userRepo.findByNameContaining(keyword);

        List<UserDto> list = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        logger.info("Completed  dao call for the search the User with keyword:{}", keyword);
        return list;
    }


}
