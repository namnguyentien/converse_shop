package com.example.converse.controller.admin;

import com.example.converse.entity.User;
import com.example.converse.payload.request.UpdateProfileReq;
import com.example.converse.security.CustomUserDetails;
import com.example.converse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;


    @GetMapping("/admin/account")
    public String getAccount(Model model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("user", user);
        return "admin/account";
    }

    @PostMapping("/api/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        userService.updateProfile(user, req);
        return ResponseEntity.ok("Cập nhật thành công");
    }


}
