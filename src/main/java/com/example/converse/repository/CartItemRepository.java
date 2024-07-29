package com.example.converse.repository;

import com.example.converse.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(long id);
}
