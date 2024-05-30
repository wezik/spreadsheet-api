package com.spreadsheet.api.controller;

import com.spreadsheet.api.dto.UserRegistrationDto;
import com.spreadsheet.api.exception.InvalidDataException;
import com.spreadsheet.api.exception.UserExistsException;
import com.spreadsheet.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto dto) {
        try {
            authService.register(dto.getUsername(), dto.getPassword(), dto.getEmail());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("login")
    public ResponseEntity<Void> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(@RequestParam("token") String token) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


}
