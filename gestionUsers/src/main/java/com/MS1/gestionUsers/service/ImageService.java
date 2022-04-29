package com.bac.tn.gestionusers.service;

import com.MS1.gestionUsers.models.Image;
import com.MS1.gestionUsers.models.User;
import com.MS1.gestionUsers.repository.ImageRepository;
import com.MS1.gestionUsers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class ImageService {
    @Autowired
    private ImageService imageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    public Image storeWithHome(MultipartFile file, long userId) throws IOException {
        Image result = null;
        User user = userRepository.findById(userId).get();
        System.out.print(user);
        if (user != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, userId, file.getContentType(), file.getBytes());
            user.setImages(image);
            userRepository.save(user);
            result = imageRepository.save(image);
            System.out.print(result);
        }

        return result;
    }

    public Image updateUserImg(MultipartFile file, long userId) throws IOException {
        Image result = null;
        User user = userRepository.findById(userId).get();
        Image img = imageRepository.findByUserId(userId);
        System.out.print(user);
        if (user != null && img != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            img.setName(fileName);
            img.setData(file.getBytes());
            img.setType(file.getContentType());
            //Image image = new Image(fileName, user, file.getContentType(), file.getBytes());

            result = imageRepository.save(img);
            System.out.print(result);
        }

        return result;
    }

    public Image getFile(String id) {
        return imageRepository.findById(id).get();
    }

    public Stream<Image> getAllFiles() {
        return imageRepository.findAll().stream();
    }
}
