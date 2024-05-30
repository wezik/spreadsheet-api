package com.spreadsheet.api.service.authentication;

import com.spreadsheet.api.dto.authentication.AuthenticationRequest;
import com.spreadsheet.api.dto.authentication.AuthenticationResponse;
import com.spreadsheet.api.dto.authentication.RegistrationRequest;
import com.spreadsheet.api.exception.UserExistsException;
import com.spreadsheet.api.model.authentication.Role;
import com.spreadsheet.api.model.authentication.User;
import com.spreadsheet.api.repository.UserRepository;
import com.spreadsheet.api.util.security.JwtUtil;
import com.spreadsheet.api.validator.RegistrationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RegistrationRequestValidator registrationRequestValidator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest request) throws UserExistsException, IllegalArgumentException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserExistsException();
        }

        registrationRequestValidator.isValid(request);

        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRepository.count() == 0 ? Role.ADMIN : Role.USER);
        user = userRepository.save(user);

        var response = new AuthenticationResponse();
        response.setToken(jwtUtil.generateToken(user));
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Invalid password");
        }

        var token = jwtUtil.generateToken(user);
        var response = new AuthenticationResponse();
        response.setToken(token);
        return response;
    }

}
