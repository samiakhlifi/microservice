package com.bac.tn.gestionusers.controllers;

import com.MS1.gestionUsers.dto.response.UserDto;
import com.MS1.gestionUsers.dto.response.UserPostDTO;
import com.MS1.gestionUsers.dto.response.userToActivityDTO;
import com.MS1.gestionUsers.mapper.UserMapper;
import com.MS1.gestionUsers.mapper.mapUserToActivity;
import com.MS1.gestionUsers.models.ERole;
import com.MS1.gestionUsers.models.Role;
import com.MS1.gestionUsers.models.User;
import com.MS1.gestionUsers.payload.request.SignupRequest;
import com.MS1.gestionUsers.payload.response.MessageResponse;
import com.MS1.gestionUsers.repository.RoleRepository;
import com.MS1.gestionUsers.repository.UserRepository;
import com.MS1.gestionUsers.security.service.UserDetailsImpl;
import com.MS1.gestionUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/service")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    mapUserToActivity mapUserToActivity;


    UserMapper UserMapper = new UserMapper();

    //Afficher la liste des utilisateurs
    @GetMapping("/users/listUserActivity")

    public ResponseEntity<List<userToActivityDTO>> sendAllUsersActivity() {
        try {
            List<userToActivityDTO> users = mapUserToActivity.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //verify user in other microservice
    @GetMapping("/users/oneUser/{id}")
    public userToActivityDTO getUser(@PathVariable("id") Long id) {
        Optional<User> userData = userRepository.findById(id);
        userToActivityDTO user = mapUserToActivity.convertToUserDTO(userData.get());
        if (userData.isPresent()) {
            return user;
        } else {
            return null;
        }
    }

    //verify user in other microservice
    @GetMapping("/users/findUser/{id}")
    public Boolean findUser(@PathVariable("id") Long id) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/users/check")
    public String test() {
        return "working emna aaaa";
    }

    //Afficher la liste des utilisateurs
    @GetMapping("/users/list")

    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();


            userRepository.findAll().forEach(users::add);


            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get list of users dto
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDto>> AllUsers() {
        List<User> activity = userService.getAll();
        List<UserDto> listActiviteDTO = UserMapper.mapToListDto(activity);
        return new ResponseEntity<>(listActiviteDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/userDTO/{id}")
    public UserDto getMaisonById(@PathVariable("id") Long id) {
        Optional<User> userData = userService.getImageById(id);
        UserDto userDTO = UserMapper.mapToDto(userData.get());

        if (userDTO != null) {

            return userDTO;
        } else {
            return null;
        }
    }

    //Afficher un user par id
    @GetMapping("/users/{id}")

    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //create user
    @PostMapping("/users/add")

    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getNom(),
                signUpRequest.getPrenom(),
                signUpRequest.getNumTel(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                "pending");

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "prop":
                        Role modRole = roleRepository.findByName(ERole.ROLE_PROPRITAIRE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    case "leader":
                        Role actRole = roleRepository.findByName(ERole.ROLE_LEADEUR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(actRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    //update user
    @PutMapping("/users/{id}")

    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserPostDTO user) {
        User userData = userRepository.findById(id).get();

        if (userData != null) {

            userData.setNom(user.getNom());
            userData.setPrenom(user.getPrenom());
            userData.setNumTel(user.getNumTel());
            userData.setEmail(user.getEmail());
            return new ResponseEntity<>(userRepository.save(userData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //changer password oublié
    @PutMapping("/users/mdp/{email}")

    public ResponseEntity<User> updateUserPassword(@PathVariable("email") String email, @RequestBody User user) {
        Optional<User> userData = userRepository.findByEmail(user.getEmail());

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setMdp(encoder.encode(user.getMdp()));
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete users
    @DeleteMapping("/users/{id}")

    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
