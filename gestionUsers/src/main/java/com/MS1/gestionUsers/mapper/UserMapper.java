package com.bac.tn.gestionusers.mapper;

import com.MS1.gestionUsers.dto.response.UserDto;
import com.MS1.gestionUsers.models.Image;
import com.MS1.gestionUsers.models.User;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public UserDto mapToDto(User entity) {
        UserDto res = new UserDto();
        if (entity != null) {
            res.setId(entity.getId());
            res.setNom(entity.getNom());
            res.setPrenom(entity.getPrenom());
            res.setEmail(entity.getEmail());
            res.setMdp(entity.getMdp());
            res.setNumTel(entity.getNumTel());
            res.setState(entity.getState());
            res.setRole(entity.getRoles());
            res.setImages(convertImages(entity.getImages()));

        }
        return res;
    }

    //Convertie une liste des users en Liste userDTO
    public List<UserDto> mapToListDto(List<User> users) {
        List<UserDto> res = users.stream().map(user -> {
            return mapToDto(user);
        }).collect(Collectors.toList());
        return res;
    }


    private String convertImages(Image images) {
        String res = null;
        if (images != null) {

            String encodedString = Base64.getEncoder().encodeToString(images.getData());
            String image = ("data:image/jpeg;base64," + encodedString);
            return image;

        }
        return res;
    }
}
