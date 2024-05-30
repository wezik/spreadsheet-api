package com.spreadsheet.api.service;

import com.spreadsheet.api.exception.InvalidDataException;
import com.spreadsheet.api.exception.UserExistsException;
import com.spreadsheet.api.model.User;
import com.spreadsheet.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String username, String password, String email) throws UserExistsException, InvalidDataException {
        if (userRepository.existsByEmail(email)) {
            throw new UserExistsException();
        }
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            throw new InvalidDataException();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        userRepository.save(user);
    }

}
