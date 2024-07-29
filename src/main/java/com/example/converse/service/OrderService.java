package com.example.converse.service;

import com.example.converse.entity.Order;
import com.example.converse.entity.User;
import com.example.converse.payload.request.CreateOrderReq;
import com.example.converse.payload.request.CreateUserReq;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> getListOrder(Integer pageNo, Integer pageSize, String sortBy);

    void saveOrder(User user, CreateOrderReq req);

    Order updateShippedDate(long id);

}
