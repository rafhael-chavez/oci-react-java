package com.springboot.MyTodoList.service;

import com.springboot.MyTodoList.controller.ToDoItemBotController;

import com.springboot.MyTodoList.controller.UserController;
import com.springboot.MyTodoList.model.ToDoItem;
import com.springboot.MyTodoList.model.UserModel;
import com.springboot.MyTodoList.repository.UserRepository;
import com.springboot.MyTodoList.util.BotCommands;
import com.springboot.MyTodoList.util.BotHelper;
import com.springboot.MyTodoList.util.BotLabels;
import com.springboot.MyTodoList.util.BotMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.bots.AbsSender;


import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    private UserController userController;

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<UserModel> getUserById(int id) {
        Optional<UserModel> userData = userRepository.findById(id);
        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public UserModel addUser(UserModel user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserModel updateUser(int id, UserModel newUser) {
        Optional<UserModel> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            UserModel user = userData.get();
            user.setId(id);
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setRegisteredTimestamp(newUser.getRegisteredTimestamp());
            user.setActive(newUser.isActive());
            return userRepository.save(user);
        } else {
            return null;
        }
    }



}
