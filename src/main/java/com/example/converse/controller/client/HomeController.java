package com.example.converse.controller.client;

import com.example.converse.entity.Product;
import com.example.converse.repository.ProductRepository;
import com.example.converse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String getHome(Model model) {
        List<Product> listProduct = productService.getListNewProduct();
        List<Product> listProductByCategory = productService.getListProductByCategoryId(4);
        List<Product> listProductByCategory2 = productService.getListProductByCategoryId(2);
        List<Product> listProductByCost = productService.getListProductByCost();
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("listProductByCategory2", listProductByCategory2);
        model.addAttribute("listProductCategory", listProductByCategory);
        model.addAttribute("listProductByCost", listProductByCost);
        return "client/index";
    }

    @GetMapping("/products/category/{id}")
    public String getProductByCategoryId(@PathVariable int id, Model model, @RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "12") Integer pageSize,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        List<Product> newProducts = productService.getNewListProductByCategoryId(id);
        Page<Product> listProduct = productService.getListProductByCategoryId(id, pageNo, pageSize, sortBy);
        int totalPages = listProduct.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("newProductList", newProducts);
        return "client/list-product";
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "client/product-detail";
    }

    @GetMapping("/product/category/{id}")
    public String getListProductByCategoryId(@PathVariable long id,Model model,
                                             @RequestParam(defaultValue = "0")Integer pageNo,
                                             @RequestParam(defaultValue = "12")Integer pageSize,
                                             @RequestParam(defaultValue = "id")String sortBy){
        List<Product> newProducts = productService.getNewListProductByCategoryId(id);
        Page<Product> listProduct = productService.getListProductByCategoryId(id, pageNo, pageSize, sortBy);
        int totalPages = listProduct.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("newProductList", newProducts);
        return "client/list-product";
    }


}
