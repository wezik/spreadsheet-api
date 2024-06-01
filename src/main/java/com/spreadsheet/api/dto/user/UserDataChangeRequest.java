package com.spreadsheet.api.dto.user;

import lombok.Data;

@Data
public class UserDataChangeRequest {
    private String username;
    private String password;
}
