package com.spreadsheet.api.config.security;

import org.springframework.http.HttpMethod;

public record RequestPermissions(HttpMethod httpMethod, String endpoint, String minimumRole) {
}
