package com.example.converse.service.impl;

import com.example.converse.entity.*;
import com.example.converse.payload.request.CreateOrderReq;
import com.example.converse.payload.request.CreateUserReq;
import com.example.converse.repository.*;
import com.example.converse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Order> getListOrder(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Order> listOrder = orderRepository.findAll(pageable);
        return listOrder;
    }

    @Override
    @Transactional
    public void saveOrder(User user, CreateOrderReq req) {
        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setRequiredDate(new Date());
        order.setUser(user);
        ShoppingCart cart = user.getShoppingCart();
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        for (CartItem item : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setPrice(item.getPrice());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.getTotalPrice();
            orderDetail.setOrder(order);
            Product product = productRepository.findById(item.getProduct().getId()).get();
            int updateQuantity = product.getQuantity() - item.getQuantity();
            if (updateQuantity < 0) {
                throw new RuntimeException("Không đủ hàng!");
            }
            product.setQuantity(updateQuantity);
            productRepository.save(product);
            orderDetailRepository.save(orderDetail);
            cartItemRepository.delete(item);
        }
        order.setTotalPrices(cart.getTotalPrices());
        order.setTotalItems(cart.getTotalItems());
        order.setName(req.getName());
        order.setEmail(req.getEmail());
        order.setCountry(req.getCountry());
        order.setAddress(req.getAddress());
        order.setPhone(req.getPhone());
        order.setNote(req.getNote());
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        shoppingCartRepository.save(cart);
        orderRepository.save(order);

    }

    @Override
    public Order updateShippedDate(long id) {
        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).get();
        order.setShippedDate(new Date());
        return orderRepository.save(order);
    }

}
