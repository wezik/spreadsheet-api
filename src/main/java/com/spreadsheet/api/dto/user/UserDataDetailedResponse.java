package com.spreadsheet.api.dto.user;

import com.spreadsheet.api.model.authentication.Role;
import lombok.Data;

@Data
public class UserDataDetailedResponse {
    private String username;
    private String password;
    private Role role;
    private boolean enabled;
}
