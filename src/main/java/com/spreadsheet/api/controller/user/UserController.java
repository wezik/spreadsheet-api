package com.spreadsheet.api.controller.user;

import com.spreadsheet.api.dto.user.UserDataResponse;
import com.spreadsheet.api.service.authentication.AuthenticationService;
import com.spreadsheet.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authService;

    @GetMapping
    public ResponseEntity<?> getCurrentUser() {
        var auth = authService.getAuthentication();
        if (auth.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        var userOpt = userService.findByUsername(auth.get().getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var user = userOpt.get();
        var response = new UserDataResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        return ResponseEntity.ok(response);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
