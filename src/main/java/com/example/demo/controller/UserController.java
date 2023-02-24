package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.AuthSuccessResponseDto;
import com.example.demo.payload.AuthUserDto;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class UserController {

    private UserService userService;
    private UserRepository userRepository ;
    private TokenRepository tokenRepository;

    // build create User REST API
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if (user.getUser_id() ==null || user.getLogin() ==null || user.getPassword() ==null ){
            return  new ResponseEntity<>(new ApiResponse("The body is empty, please fill all the  required fields",false),HttpStatus.BAD_REQUEST);
        }
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("authentication-header") String header){
//
//        User savedUser = userService.createUser(user);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthUserDto user){
        if (user.getLogin() ==null || user.getPassword() ==null ){
            return  new ResponseEntity<>(new ApiResponse("The body is empty, please fill all the  required fields",false),HttpStatus.BAD_REQUEST);
        }
        Optional<User> user1 = userRepository.findByLogin(user.getLogin());
        if(user1==null){
            return new ResponseEntity<>(new ApiResponse("user not found with that login",false),HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user1.get().getPassword(), user.getPassword())){
            return new ResponseEntity<>(new ApiResponse("Incorrect password",false),HttpStatus.UNAUTHORIZED);
        }
        String randomToken = String.valueOf(new UUID(20,10));
        AuthSuccessResponseDto authSuccessResponseDto = new AuthSuccessResponseDto(randomToken);

        Token token = new Token();
        token.setUser_id(user1.get().getUser_id());
        token.setValid(true);
        token.setTokenData(randomToken);

        tokenRepository.save(token);
        return new ResponseEntity<>(authSuccessResponseDto, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId){
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Build Update User REST API


    // Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
}
