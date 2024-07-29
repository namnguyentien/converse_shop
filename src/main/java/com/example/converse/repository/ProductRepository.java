package com.example.converse.repository;

import com.example.converse.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Lấy danh sách sản phẩm
    @Query(value = "SELECT * FROM Product ORDER BY id LIMIT 8", nativeQuery = true)
    List<Product> getNewListProduct();

    //Lấy danh sách sản phẩm theo loại và trả ra page
    Page<Product> getProductByCategoryId(Long id, Pageable pageable);

    //Lấy ra sản phẩm mới theo loại
    @Query(value = "SELECT * FROM Product WHERE category_id = :id ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Product> getNewProductByCategoryId(Long id);

    //Lấy sản phẩm theo loại
    @Query(value = "SELECT * FROM Product WHERE category_id = :id ORDER BY id DESC LIMIT 8",nativeQuery = true)
    List<Product> getProductByCategoryId(Long id);

    //Lấy sản phẩm theo giá
    @Query(value = "Select * from Product order by price limit 8 ",nativeQuery = true)
    List<Product> getListProductByCost();











}
