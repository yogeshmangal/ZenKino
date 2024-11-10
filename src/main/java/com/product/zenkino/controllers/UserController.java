package com.product.zenkino.controllers;

import com.product.zenkino.entities.User;
import com.product.zenkino.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/user")
    public User createuser(@RequestBody User user) {
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        Optional<User> currentUserOptional = userRepository.findById(id);
        if (currentUserOptional.isEmpty())
            return ResponseEntity.notFound().build();
        User currentUser = currentUserOptional.get();
        modelMapper.map(user, currentUser);
        currentUser.setUpdatedAt(new Date());
        userRepository.save(currentUser);
        return ResponseEntity.ok(currentUser);
    }
    //TODO: Design GET endpoint, DELETE endpoint, GETById endpoint and exception handling.
}
