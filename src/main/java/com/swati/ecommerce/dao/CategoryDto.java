package com.swati.ecommerce.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends CusomFieldsDto{

    private String categoryId;
    @NotBlank
    @Size(min = 3, message = "title must be of minimum 3 characters!!")
    private String title;
    @NotBlank(message = "Description is required!!")
    private String description;
    @NotBlank(message = "Cover image is required!!")
    private String coverImage;
}
