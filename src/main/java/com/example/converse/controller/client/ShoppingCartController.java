package com.example.converse.controller.client;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.AddProductToCartReq;
import com.example.converse.security.CustomUserDetails;
import com.example.converse.service.ProductService;
import com.example.converse.service.ShoppingCartService;
import com.example.converse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal) {

        if (principal != null) {
            User user = userService.getUser(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            if (cart != null) {
                List<CartItem> cartItems = shoppingCartService.getListCartItem(cart.getId());
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("cart", cart);
            }
        }
        return "client/cart";
    }


    @PostMapping("/client/api/add-to-cart")
    public ResponseEntity<?> addItemToCart(@Valid @RequestBody AddProductToCartReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (user == null) {
            return ResponseEntity.badRequest().body("Lỗi");
        }
        ShoppingCart cart = user.getShoppingCart();
        if (cart == null) {
            cart = shoppingCartService.addShoppingCartFirstTime(req, user);
        } else {
            cart = shoppingCartService.addToExistingShoppingCart(req, user);
        }

        return ResponseEntity.ok("Thêm thành công");

    }

    @PutMapping("/client/api/update-cart")
    public ResponseEntity<?> updateItemCart(@Valid @RequestBody AddProductToCartReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        ShoppingCart cart = user.getShoppingCart();
        cart = shoppingCartService.updateItemShoppingCart(req, user);
        return ResponseEntity.ok("Cập nhật thành công");
    }


    @DeleteMapping("/client/api/delete-item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        ShoppingCart cart = user.getShoppingCart();
        cart = shoppingCartService.deleteItemShoppingCart(id, user);
        return ResponseEntity.ok("Xóa thành công");
    }

    @DeleteMapping("/client/api/clear-cart")
    public ResponseEntity<?> clearCart() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        ShoppingCart cart = user.getShoppingCart();
        shoppingCartService.clearCart(cart.getId(), user);
        return ResponseEntity.ok("Xóa thành công");
    }
}