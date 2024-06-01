package com.spreadsheet.api.controller.user;

import com.spreadsheet.api.dto.user.UserDataChangeRequest;
import com.spreadsheet.api.dto.user.UserDataDetailedResponse;
import com.spreadsheet.api.dto.user.UserDataResponse;
import com.spreadsheet.api.service.authentication.AuthenticationService;
import com.spreadsheet.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authService;
    private final PasswordEncoder passwordEncoder;

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
        var response = userService.getAllUsers().stream().map(e -> {
            var dto = new UserDataDetailedResponse();
            dto.setUsername(e.getUsername());
            dto.setRole(e.getRole());
            dto.setPassword(e.getPassword());
            dto.setEnabled(e.isEnabled());
            return dto;
        }).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDataChangeRequest request) {
        var auth = authService.getAuthentication();
        if (auth.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        var userOpt = userService.findByUsername(auth.get().getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var user = userOpt.get();
        if (request.getPassword() != null) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password cannot be the same as the old one");
            }
            user.setPassword(request.getPassword());
        }
        if (request.getUsername() != null) {
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
            }
            user.setUsername(request.getUsername());
        }

        userService.updateUser(user);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam(name = "username") String username) {
        var exists = userService.existsByUsername(username);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted");
    }

}
