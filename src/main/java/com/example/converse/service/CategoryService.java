package com.example.converse.service;

import com.example.converse.entity.Category;
import com.example.converse.payload.request.CreateCategoryReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryService {

    List<Category> getListCategory();

    List<Category> getListEnable();

    Category findCategoryById(long id);

    Category createCategory(CreateCategoryReq req);

    void updateCategory(long id, CreateCategoryReq req);

    void deleteCategory(long id);

    void enabledCategory(long id);

}
