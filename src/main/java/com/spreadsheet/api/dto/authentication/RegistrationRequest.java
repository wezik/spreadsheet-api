package com.spreadsheet.api.dto.authentication;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
}
