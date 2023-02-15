package com.example.redisbase.service;

import com.example.redisbase.dto.AddUserDTO;
import com.example.redisbase.dto.AuthUserDTO;
import com.example.redisbase.dto.GenericResponse;
import com.example.redisbase.entity.RedisUser;
import com.example.redisbase.rep.UserRepository;
import com.example.redisbase.util.RedisUtility;
import com.google.gson.Gson;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private RedisUtility utility;

    public GenericResponse addUser(AddUserDTO addUserDTO){
        RedisUser user = new RedisUser();
        user.setEmail(addUserDTO.emailId);
        user.setPassword(addUserDTO.password);
        return new GenericResponse("User success",userRepository.save(user));
    }

    public GenericResponse authUser(AuthUserDTO authUserDTO){
        AuthUserDTO authUserDTO1= utility.getValue(authUserDTO.emailId);

        if (authUserDTO1==null){
            if (userRepository.findByEmailAndPassword(authUserDTO.emailId, authUserDTO.password).isPresent()){
                utility.setValue(authUserDTO.emailId,authUserDTO);
                return new GenericResponse("Getting data from database",authUserDTO);
            }
            return new GenericResponse("Not Found",authUserDTO);
        }
        return new GenericResponse("get RedisServer",authUserDTO);
    }

}
