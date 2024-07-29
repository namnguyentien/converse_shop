package com.example.converse.controller;

import com.example.converse.entity.Product;
import com.example.converse.entity.User;
import com.example.converse.payload.request.ChangePasswordReq;
import com.example.converse.payload.request.CreateUserReq;
import com.example.converse.payload.request.LoginRequest;
import com.example.converse.payload.response.MessageResponse;
import com.example.converse.repository.UserRepository;
import com.example.converse.security.CustomUserDetails;
import com.example.converse.security.JwtTokenUtil;
import com.example.converse.service.ProductService;
import com.example.converse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //sau 7 ngày thoát ta khoản phải đăng nhập lại
    public static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req, HttpServletResponse response, HttpSession session) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),
                        req.getPassword()));

        String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);

        session.setAttribute("authentication", authentication);
        session.setMaxInactiveInterval(MAX_AGE_COOKIE);

        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already token!"));
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = userService.registerUser(req);
        return ResponseEntity.ok(new MessageResponse("User register successfully"));

    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordReq req) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.status(404).body("Người dùng không tồn tại!");
        }

        userService.changePassword(user, req);
        return ResponseEntity.ok("Mật khẩu đã thay đổi thành công!");
    }

}
