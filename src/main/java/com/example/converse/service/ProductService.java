package com.example.converse.service;

import com.example.converse.entity.Product;
import com.example.converse.payload.request.CreateProductReq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> getListProduct();

    List<Product> getListNewProduct();

    List<Product> getListProductByCost();

    Page<Product> getPageProduct(Integer pageNo, Integer pageSize, String sortBy);

    Page<Product> getListProductByCategoryId(long id, Integer pageNo, Integer pageSize, String sortBy);

    List<Product> getNewListProductByCategoryId(long id);

    List<Product> getListProductByCategoryId(long id);

    Product findProductById(long id);

    Product createProduct(CreateProductReq req);

    Product updateProduct(long id, CreateProductReq req);

    void deleteProduct(long id);

}
