package com.codewitharjun.fullstackbackend.controller;
import com.codewitharjun.fullstackbackend.exception.UserNotFoundException;
import  com.codewitharjun.fullstackbackend.model.User;
import com.codewitharjun.fullstackbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return  userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUser(){
        return  userRepository.findAll();
    }
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return  userRepository.findById(id)
                .map(user->{
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    user.setName(newUser.getName());
                    return  userRepository.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with Id "+id+" has been deleted ";
    }


}
