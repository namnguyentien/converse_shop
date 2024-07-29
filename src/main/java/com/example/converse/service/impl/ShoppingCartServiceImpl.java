package com.example.converse.service.impl;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.Product;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.AddProductToCartReq;
import com.example.converse.repository.CartItemRepository;
import com.example.converse.repository.ProductRepository;
import com.example.converse.repository.ShoppingCartRepository;
import com.example.converse.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ShoppingCart addShoppingCartFirstTime(AddProductToCartReq req, User user) {
        ShoppingCart cart = new ShoppingCart();
        shoppingCartRepository.save(cart);
        Product product = productRepository.findById(req.getProductId()).get();
        CartItem cartItem = new CartItem();
        cartItem.setPrice(product.getPrice());
        cartItem.setProduct(product);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setTotalPrice(req.getQuantity() * product.getPrice());
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        cart.setUser(user);

        cart.setTotalItems(totalItemInCart(cart));
        cart.setTotalPrices(totalPriceInCart(cart));

        return shoppingCartRepository.saveAndFlush(cart);
    }

    public long totalPriceInCart(ShoppingCart cart) {
        long totalPrice = 0;
        List<CartItem> cartItems = getListCartItem(cart.getId());
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    public int totalItemInCart(ShoppingCart cart) {
        int totalItem = 0;
        List<CartItem> cartItems = getListCartItem(cart.getId());
        for (CartItem item : cartItems) {
            totalItem += 1;
        }
        return totalItem;
    }

    @Override
    public ShoppingCart addToExistingShoppingCart(AddProductToCartReq req, User user) {
        // TODO Auto-generated method stub
        ShoppingCart cart = user.getShoppingCart();
        Product product = productRepository.findById(req.getProductId()).get();
        List<CartItem> cartItems = getListCartItem(cart.getId());
        Boolean existed = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                existed = true;
                item.setQuantity(item.getQuantity() + req.getQuantity());
                item.setTotalPrice(item.getTotalPrice() + req.getQuantity() * product.getPrice());
                cartItemRepository.saveAndFlush(item);
            }
        }

        if (existed == false) {
            CartItem cartItem = new CartItem();
            cartItem.setPrice(product.getPrice());
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setTotalPrice(req.getQuantity() * product.getPrice());
            cartItem.setCart(cart);
            cartItemRepository.saveAndFlush(cartItem);
        }

        cart.setTotalItems(totalItemInCart(cart));
        cart.setTotalPrices(totalPriceInCart(cart));
        return shoppingCartRepository.save(cart);

    }

    @Override
    public List<CartItem> getListCartItem(long id) {
        // TODO Auto-generated method stub
        List<CartItem> cartItems = cartItemRepository.findByCartId(id);
        return cartItems;
    }

    @Override
    public ShoppingCart updateItemShoppingCart(AddProductToCartReq req, User user) {
        // TODO Auto-generated method stub
        Product product = productRepository.findById(req.getProductId()).get();
        ShoppingCart cart = user.getShoppingCart();
        List<CartItem> cartItems = getListCartItem(cart.getId());

        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(req.getQuantity());
                item.setTotalPrice(req.getQuantity() * product.getPrice());
                cartItemRepository.save(item);
            }
        }
        cart.setTotalItems(totalItemInCart(cart));
        cart.setTotalPrices(totalPriceInCart(cart));
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart deleteItemShoppingCart(long id, User user) {
        Product product = productRepository.findById(id).get();
        ShoppingCart cart = user.getShoppingCart();
        List<CartItem> cartItems = getListCartItem(cart.getId());
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                cartItemRepository.delete(item);
            }
        }
        cart.setTotalItems(totalItemInCart(cart));
        cart.setTotalPrices(totalPriceInCart(cart));
        return shoppingCartRepository.save(cart);
    }

    @Override
    public void clearCart(long id, User user) {
        // TODO Auto-generated method stub
        ShoppingCart cart = user.getShoppingCart();
        List<CartItem> cartItems = getListCartItem(cart.getId());
        cartItemRepository.deleteAll(cartItems);

    }

}
