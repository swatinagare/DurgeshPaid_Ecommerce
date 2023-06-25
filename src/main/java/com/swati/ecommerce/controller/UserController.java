package com.swati.ecommerce.controller;

import com.swati.ecommerce.dao.UserDto;
import com.swati.ecommerce.helper.AppConstant;
import com.swati.ecommerce.payloads.ApiResponse;
import com.swati.ecommerce.payloads.ImageResponse;
import com.swati.ecommerce.payloads.PageableResponse;
import com.swati.ecommerce.service.FileService;
import com.swati.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;
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
     * @return List</ userDto>
     * @author Swati Nagare
     * @apiNote this is the api related to get All user
     */
    @GetMapping("/users")
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        logger.info("Request Entering for get All  user");
        PageableResponse<UserDto> getAllUser = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete Request for get All  user ");
        return new ResponseEntity<>(getAllUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return userDto
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
     * @return userDto
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
     * @return ApiResponse
     * @author Swati Nagare
     * @apiNote this is the api related to delete user by userId
     */
    @DeleteMapping("users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        logger.info("Request Entering for delete single user with userId :{}", userId);
        this.userService.deleteUser(userId);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(AppConstant.USER_DELETE);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);
        logger.info("Complete Request for delete single user with userId :{}", userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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

    /**
     * @param image,userId
     * @return
     * @author Swati Nagare
     * @apiNote this is the api related to uploadImage  by userId
     */

    @PostMapping("users/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage") MultipartFile image,
                                                     @PathVariable String userId) throws IOException {

        logger.info("Request Entering for upload image with User id: {} & saving image into DB", userId);
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto updatedUser = userService.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).message(AppConstant.FILE_UPLOADED).build();
        logger.info("Completed request for upload image with User id: {}", userId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return image
     * @author Swati Nagare
     * @apiNote this is the api related to serveImage by userId
     */
    @GetMapping("/users/image/{userId}")
    public void serveImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        logger.info("Request Entering for download image with User id: {}", userId);
        UserDto userDto = userService.getUserById(userId);
        logger.info("User image name : {}", userDto.getImageName());
        InputStream inputStream = fileService.getResource(imageUploadPath, userDto.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());  // StreamUtils copy data from inputStream to servlet response ie. it will write data to Servlet response
        logger.info("Completed request for download image with User id: {}", userId);
    }



}
