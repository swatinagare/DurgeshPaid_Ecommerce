package com.swati.ecommerce.dao;

import com.swati.ecommerce.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;

    @NotBlank
    @Size(min = 4, max = 15, message = "Plz enter Valid UserName Min=4 or Max=15")
    private String name;

    //@Email(message = "Email Is not according to standards..")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email..!!")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is Required")
    @Pattern(regexp = "^[a-z1-9]{2,10}")
    private String password;

    @NotBlank(message = "Specify gender!!")
    private String gender;

    @Size(min = 10, max = 1000)
    @NotBlank(message = "Write something about Yourself!!")
    private String about;

    @ImageNameValid
    private String imageName;
}
