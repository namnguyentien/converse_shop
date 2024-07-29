package com.example.converse.controller.admin;

import com.example.converse.entity.Category;
import com.example.converse.entity.Image;
import com.example.converse.entity.Product;
import com.example.converse.payload.request.CreateProductReq;
import com.example.converse.service.CategoryService;
import com.example.converse.service.ImageService;
import com.example.converse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;


    @GetMapping("/admin/product")
    public String getListProduct(Model model, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "8") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        Page<Product> listProduct = productService.getPageProduct(pageNo, pageSize, sortBy);
        List<Category> listCategory = categoryService.getListCategory();
        List<Image> listImage = imageService.getAllImages();
        int totalPages = listProduct.getTotalPages();

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listImage", listImage);
        return "admin/product";
    }

    @GetMapping("/api/product/pageProduct")
    public ResponseEntity<?> getPageProduct(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "8") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        Page<Product> productList = productService.getPageProduct(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/api/product/pageByCategoryId/{id}")
    public ResponseEntity<?> getListProductByCategory(@PathVariable long id, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "12") Integer pageSize, @RequestParam(defaultValue = "id") String sortBt) {
        Page<Product> productList = productService.getListProductByCategoryId(id, pageNo, pageSize, sortBt);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }


    @PostMapping("/api/product/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductReq req) {
        Product product = productService.createProduct(req);
        return ResponseEntity.ok(product);

    }

    @PutMapping("/api/product/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @Valid @RequestBody CreateProductReq req) {
        Product product = productService.updateProduct(id, req);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/api/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công");
    }


}
