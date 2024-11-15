package com.product.zenkino.controllers;

import com.product.zenkino.entities.User;
import com.product.zenkino.exception.EntityNotFoundException;
import com.product.zenkino.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new EntityNotFoundException("User with Id " + id + " not found");
        return user;
    }

    @PostMapping("/user")
    public User createuser(@RequestBody User user) {
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        Optional<User> currentUserOptional = userRepository.findById(id);
        if (currentUserOptional.isEmpty())
            throw new EntityNotFoundException("User with Id " + id + " not found");
        User currentUser = currentUserOptional.get();
        modelMapper.map(user, currentUser);
        currentUser.setUpdatedAt(new Date());
        return userRepository.save(currentUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new EntityNotFoundException("User with Id " + id + " not found");
        userRepository.deleteById(id);
    }
}
