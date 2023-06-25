package com.swati.ecommerce.service.impl;

import com.swati.ecommerce.dao.CategoryDto;
import com.swati.ecommerce.entity.Category;
import com.swati.ecommerce.exception.ResourceNotFoundException;
import com.swati.ecommerce.helper.AppConstant;
import com.swati.ecommerce.helper.PageHelper;
import com.swati.ecommerce.payloads.PageableResponse;
import com.swati.ecommerce.repository.CategoryRepo;
import com.swati.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.profile.image.Path}")
    private String imagePath;

    /**
     * @param categoryDto
     * @return
     * @author Swati Nagare
     * @implNote this is the impl related to save category
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Initiating dao call for the create  the Category");
        String randomId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(randomId);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);
        logger.info("Completed  dao call for the create  the Category ");
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    /**
     * @param categoryId
     * @return categoryDto
     * @author Swati Nagare
     * @implNote this is the impl related to update Category by categoryId
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating dao call for the update the Category with categoryId :{}", categoryId);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        category.setCreatedBy(categoryDto.getCreatedBy());
        category.setUpdatedBy(categoryDto.getUpdatedBy());
        category.setIsActive(categoryDto.getIsActive());
        Category updatedCategory = categoryRepo.save(category);
        logger.info("Completed  dao call for the update the Category with categoryId:{}", categoryId);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }


    /**
     * @return List</ categoryDto>
     * @author Swati Nagare
     * @implNote this is the impl related to get All Category
     */
    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao call for the get All  the Category");
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Category> categoryPage = categoryRepo.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = PageHelper.getPageableResponse(categoryPage, CategoryDto.class);
        logger.info("Completed  dao call for the get All  the Category ");
        return pageableResponse;
    }

    /**
     * @param categoryId
     * @return categoryDto
     * @author Swati Nagare
     * @implNote this is the impl related to get category by categoryId
     */
    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        logger.info("Initiating dao call for the get the Category with categoryId :{}", categoryId);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));
        logger.info("Completed  dao call for the get the Category with categoryId:{}", categoryId);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    /**
     * @param categoryId
     * @author Swati Nagare
     * @implNote this is the impl related to delete category by categoryId
     */
    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Initiating dao call for the delete the Category with categoryId :{}", categoryId);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));
        String fullPath = imagePath + category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            logger.info("Category cover image is not found in folder");
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        logger.info("Cover image is deleted from folder");
        categoryRepo.delete(category);
        logger.info("Completed  dao call for the delete the Category with categoryId:{}", categoryId);

    }

    /**
     * @param keywords
     * @return categoryDto
     * @author Swati Nagare
     * @implNote this is the impl related to search category by keywords
     */
    @Override
    public List<CategoryDto> searchCategory(String keywords) {
        logger.info("Initiating dao call for the search the Category with keywords :{}", keywords);
        List<Category> categories = categoryRepo.findByTitleContaining(keywords);
        List<CategoryDto> categoryDtos = categories.stream().map(i -> modelMapper.map(i, CategoryDto.class)).collect(Collectors.toList());
        logger.info("Completed  dao call for the search the Category with keywords:{}", keywords);
        return categoryDtos;
    }
}
