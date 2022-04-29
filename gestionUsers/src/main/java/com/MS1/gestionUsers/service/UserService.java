package com.bac.tn.gestionusers.service;


import com.MS1.gestionUsers.models.User;
import com.MS1.gestionUsers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getImageById(Long id) {
        System.out.println(id);
        Optional<User> res = null;
        if (id != null) {
            res = userRepository.findById(id);

        }
        return res;
    }
}
