package com.example.converse.service.impl;

import com.example.converse.entity.Category;
import com.example.converse.entity.Image;
import com.example.converse.entity.Product;
import com.example.converse.exception.NotFoundException;
import com.example.converse.payload.request.CreateProductReq;
import com.example.converse.repository.CategoryRepository;
import com.example.converse.repository.ImageRepository;
import com.example.converse.repository.ProductRepository;
import com.example.converse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public List<Product> getListProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getListNewProduct() {
        return productRepository.getNewListProduct();
    }

    @Override
    public List<Product> getListProductByCost() {
        return productRepository.getListProductByCost();
    }

    @Override
    public Page<Product> getPageProduct(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> listProduct = productRepository.findAll(pageable);
        return listProduct;
    }

    @Override
    public Page<Product> getListProductByCategoryId(long id, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> listProduct = productRepository.getProductByCategoryId(id, pageable);
        return listProduct;
    }

    @Override
    public List<Product> getNewListProductByCategoryId(long id) {
        return productRepository.getNewProductByCategoryId(id);
    }

    @Override
    public List<Product> getListProductByCategoryId(long id) {
        return productRepository.getProductByCategoryId(id);
    }

    @Override
    public Product findProductById(long id) {
        Product product = productRepository.findById(id).get();
        return product;
    }

    @Override
    public Product createProduct(CreateProductReq req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        Category category = categoryRepository.findById(req.getCategory_id()).get();
        product.setCategory(category);
        ArrayList<Image> images = new ArrayList<>();
        for (Long id : req.getImageIds()) {
            Image image = imageRepository.findById(id).get();
            images.add(image);
        }
        product.setImages(images);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long id, CreateProductReq req) {
        Optional<Product> rs = productRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        Product product = rs.get();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        Category category = categoryRepository.findById(req.getCategory_id()).get();
        product.setCategory(category);
        ArrayList<Image> images = new ArrayList<>();
        for (Long imageId : req.getImageIds()) {
            Image image = imageRepository.findById(imageId).get();
            images.add(image);
        }
        product.setImages(images);
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(long id) {
        // TODO Auto-generated method stub
        Optional<Product> rs = productRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }
        Product product = rs.get();
        product.getImages().remove(this);
        productRepository.delete(product);
    }


}
