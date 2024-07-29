package com.example.converse.controller.admin;

import com.example.converse.entity.Category;
import com.example.converse.payload.request.CreateCategoryReq;
import com.example.converse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public String category(Model model) {
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);
        return "admin/category";
    }

    @GetMapping("/api/category/enable")
    public ResponseEntity<?> getListCategoryEnabled() {
        List<Category> categories = categoryService.getListEnable();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("api/category/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryReq req) {
        Category category = categoryService.createCategory(req);
        return ResponseEntity.ok(category);
    }

    @PutMapping("api/category/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, CreateCategoryReq req) {
        categoryService.updateCategory(id, req);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PostMapping("api/category/enable/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id) {
        categoryService.enabledCategory(id);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("api/category/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Xóa thành công");
    }


}
