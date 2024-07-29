package com.example.converse.service;

import com.example.converse.entity.Image;

import java.util.List;

public interface ImageService {

    void save(Image image);

    List<Image> getAllImages();

    void deleteImage(String uploadDir, String fileName);


}
