package com.example.converse.service;

import com.example.converse.entity.User;
import com.example.converse.payload.request.ChangePasswordReq;
import com.example.converse.payload.request.CreateUserReq;
import com.example.converse.payload.request.UpdateProfileReq;

public interface UserService {

    User getUser(String username);

    User registerUser(CreateUserReq user);

    User changePassword(User user, ChangePasswordReq req);

    User updateProfile(User user, UpdateProfileReq req);

}
