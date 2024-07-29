package com.example.converse.controller.admin;

import com.example.converse.entity.Order;
import com.example.converse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    OrderService orderService;

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/order")
    public String getOrder(Model model, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "8") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        Page<Order> listOrder = orderService.getListOrder(pageNo, pageSize, sortBy);
        model.addAttribute("listOrder", listOrder);
        return "admin/order";
    }

    @PostMapping("/admin/order/{id}")
    public ResponseEntity<?> updateShippedDate(@PathVariable long id) {
        Order order = orderService.updateShippedDate(id);
        return ResponseEntity.ok(order);
    }

}
