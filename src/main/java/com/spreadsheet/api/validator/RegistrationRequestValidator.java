package com.spreadsheet.api.validator;

import com.spreadsheet.api.dto.authentication.RegistrationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationRequestValidator {

    @Value("${registration.username.min.length}")
    private int usernameMinLength;
    @Value("${registration.username.max.length}")
    private int usernameMaxLength;
    @Value("${registration.password.min.length}")
    private int passwordMinLength;
    @Value("${registration.password.max.length}")
    private int passwordMaxLength;

    public boolean isValid(RegistrationRequest request) throws IllegalArgumentException {
        List<String> errors = new ArrayList<>();
        if (!isUsernameValid(request.getUsername())) {
            errors.add("Invalid username");
        }
        if (!isPasswordValid(request.getPassword())) {
            errors.add("Invalid password");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
        return true;
    }

    private boolean isUsernameValid(String username) {
        return username != null && !username.isEmpty() && username.length() >= usernameMinLength && username.length() <= usernameMaxLength;
    }

    private boolean isPasswordValid(String password) {
        return password != null && !password.isEmpty() && password.length() >= passwordMinLength && password.length() <= passwordMaxLength;
    }

}
