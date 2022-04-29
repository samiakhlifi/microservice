package com.bac.tn.gestionusers.controllers;


import com.MS1.gestionUsers.message.ResponseImage;
import com.MS1.gestionUsers.repository.UserRepository;
import com.MS1.gestionUsers.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/imageUser")
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/add/{userId}")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file
            , @PathVariable("userId") long userId) {
        System.out.println("awel lcontroller ");
        String message = "";

        try {
            imageService.storeWithHome(file, userId);
            System.out.println("imaaage saved ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        message = "Uploaded  files successfully: ";
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @PutMapping(path = "/update/{userId}")
    public ResponseEntity<String> updateImg(@RequestParam("image") MultipartFile file
            , @PathVariable("userId") long userId) {
        System.out.println("awel lcontroller ");
        String message = "";

        try {
            imageService.updateUserImg(file, userId);
            System.out.println("imaaage saved ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        message = "Uploaded  files successfully: ";
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseImage>> getListFiles() {
        List<ResponseImage> files = imageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new ResponseImage(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
