package com.example.GajaYeogi.controller;

import com.example.GajaYeogi.dto.UserDto;
import com.example.GajaYeogi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userservice;
    private UserController(UserService userservice){this.userservice = userservice;}
    @PostMapping("/saveuser")
    public ResponseEntity<String> saveuser(@RequestParam(value = "user") String user,
                                           @RequestParam(value = "username") String username){
        try{
            UserDto userDto = new UserDto();
            userDto.setUser(user);
            userDto.setUsername(username);
            String userresponse = userservice.saveuser(userDto);

            return ResponseEntity.ok(userresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 않은 오류입니다.");
        }
    }

    @PostMapping("/readuser")
    public ResponseEntity<UserDto> readuser(@RequestParam(value = "user") String user){
        try{
            UserDto userDto = new UserDto();
            userDto.setUser(user);

            UserDto userlist = userservice.readuser(userDto);


            return ResponseEntity.ok(userlist);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
