package com.spreadsheet.api.controller;

import com.spreadsheet.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    @GetMapping("register")
    public ResponseEntity<String> register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("login")
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(@RequestParam("token") String token) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


}
