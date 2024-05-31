package com.spreadsheet.api.dto.user;

import com.spreadsheet.api.model.authentication.Role;
import lombok.Data;

@Data
public class UserDataResponse {
    private String username;
    private Role role;
}
