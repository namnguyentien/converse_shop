package com.example.converse.controller.client;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.CreateOrderReq;
import com.example.converse.security.CustomUserDetails;
import com.example.converse.service.OrderService;
import com.example.converse.service.ShoppingCartService;
import com.example.converse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/checkout")
    public String getOrderPage(Model model, Principal principal){
        if(principal != null){
            User user = userService.getUser(principal.getName());
            ShoppingCart cart =user.getShoppingCart();
            if(cart != null){
                List<CartItem> cartItem = shoppingCartService.getListCartItem(cart.getId());
                model.addAttribute("cart", cart);
                model.addAttribute("cartItem", cartItem);
            }
        }

        return "client/checkout";
    }


    @GetMapping("/success")
    public String getSuccess(){
        return "client/success";
    }

    @PostMapping("/client/api/save-order")
    public ResponseEntity<?> saveOrder(@RequestBody CreateOrderReq req){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        orderService.saveOrder(user,req);

        return ResponseEntity.ok("Thành công");
    }
}
