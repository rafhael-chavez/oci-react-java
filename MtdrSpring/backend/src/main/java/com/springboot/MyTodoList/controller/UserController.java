package com.springboot.MyTodoList.controller;

import com.springboot.MyTodoList.model.UserModel;
import com.springboot.MyTodoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<UserModel> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable int id) {
        try {
            ResponseEntity<UserModel> responseEntity = userService.getUserById(id);
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity addUser(@RequestBody UserModel user) throws Exception {
        UserModel newUser = userService.addUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/users/" + newUser.getId()));
        return ResponseEntity.created(responseHeaders.getLocation()).build();
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity updateUser(@RequestBody UserModel user, @PathVariable int id) {
        try {
            UserModel updatedUser = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") int id) {
        boolean flag = userService.deleteUser(id);
        if (flag) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

}
