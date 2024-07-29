package com.example.converse.service.impl;

import com.example.converse.entity.ERole;
import com.example.converse.entity.Role;
import com.example.converse.entity.User;
import com.example.converse.exception.BadRequestException;
import com.example.converse.payload.request.ChangePasswordReq;
import com.example.converse.payload.request.CreateUserReq;
import com.example.converse.payload.request.UpdateProfileReq;
import com.example.converse.repository.RoleRepository;
import com.example.converse.repository.UserRepository;
import com.example.converse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User registerUser(CreateUserReq req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        Set<String> strRole = req.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not Found!"));
            roles.add(userRole);
        } else {
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not Found!"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not Found!"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public User changePassword(User user, ChangePasswordReq req) {
        // TODO Auto-generated method stub
        if (!BCrypt.checkpw(req.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác!");
        }
        String hash = BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(User user, UpdateProfileReq req) {
        // TODO Auto-generated method stub
        user.setFullname(req.getFullname());
        user.setAddress(req.getAddress());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setNote(req.getNote());
        return userRepository.save(user);
    }
}
