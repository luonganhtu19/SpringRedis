package com.example.redisbase.controller;

import com.example.redisbase.dto.AddUserDTO;
import com.example.redisbase.dto.AuthUserDTO;
import com.example.redisbase.dto.GenericResponse;
import com.example.redisbase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("redis/demo")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/add")
    public GenericResponse addUser(@RequestBody AddUserDTO addUserDTO){
        return service.addUser(addUserDTO);
    }

    @PostMapping
    public GenericResponse authenticateUser(@RequestBody AuthUserDTO authUserDTO){
        return service.authUser(authUserDTO);
    }
}
