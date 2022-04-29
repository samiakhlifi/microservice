package com.bac.tn.gestionusers.mapper;

import com.MS1.gestionUsers.dto.response.userToActivityDTO;
import com.MS1.gestionUsers.models.User;
import com.MS1.gestionUsers.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class mapUserToActivity {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<userToActivityDTO> getAllUsers() {
        return ((List<User>) userRepository
                .findAll())
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    public userToActivityDTO convertToUserDTO(User user) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        userToActivityDTO userToActivityDTO = modelMapper
                .map(user, userToActivityDTO.class);
        return userToActivityDTO;
    }
}
