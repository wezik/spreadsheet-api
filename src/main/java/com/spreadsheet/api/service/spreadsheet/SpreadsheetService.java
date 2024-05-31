package com.spreadsheet.api.service.spreadsheet;

import com.spreadsheet.api.dto.spreadsheet.SpreadsheetResponse;
import com.spreadsheet.api.model.authentication.User;
import com.spreadsheet.api.model.spreadsheet.Spreadsheet;
import com.spreadsheet.api.repository.spreadsheet.SpreadsheetRepository;
import com.spreadsheet.api.service.authentication.AuthenticationService;
import com.spreadsheet.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpreadsheetService {

    private final SpreadsheetRepository spreadsheetRepository;
    private final AuthenticationService authService;
    private final UserService userService;

    private Optional<User> getCurrentUser() {
        var auth = authService.getAuthentication();
        if (auth.isEmpty()) {
            return Optional.empty();
        }

        return userService.findByUsername(auth.get().getName());
    }

    public List<SpreadsheetResponse> getCurrentUserSpreadsheets() {
        var userOpt = this.getCurrentUser();
        if (userOpt.isEmpty()) {
            return List.of();
        }
        return userOpt.get().getSpreadsheets().stream().map(spreadsheet -> {
            var spreadsheetResponse = new SpreadsheetResponse();
            spreadsheetResponse.setId(spreadsheet.getId());
            spreadsheetResponse.setTitle(spreadsheet.getTitle());
            return spreadsheetResponse;
        }).toList();
    }

    public Optional<SpreadsheetResponse> getSpreadsheetOwnedByCurrentUser(Long id) {
        var userOpt = this.getCurrentUser();
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        var spreadsheetOpt = spreadsheetRepository.findById(id);
        if (spreadsheetOpt.isEmpty()) {
            return Optional.empty();
        }

        var spreadsheet = spreadsheetOpt.get();
        if (!spreadsheet.getOwner().getUsername().equals(userOpt.get().getUsername())) {
            return Optional.empty();
        }

        var spreadsheetResponse = new SpreadsheetResponse();
        spreadsheetResponse.setId(spreadsheet.getId());
        spreadsheetResponse.setTitle(spreadsheet.getTitle());
        return Optional.of(spreadsheetResponse);
    }

    public Optional<SpreadsheetResponse> createSpreadsheetForCurrentUser(String title) {
        var userOpt = this.getCurrentUser();
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        var spreadsheet = new Spreadsheet();
        spreadsheet.setTitle(title);
        spreadsheet.setOwner(userOpt.get());
        spreadsheet = spreadsheetRepository.save(spreadsheet);

        var response = new SpreadsheetResponse();
        response.setId(spreadsheet.getId());
        response.setTitle(spreadsheet.getTitle());
        return Optional.of(response);
    }

}
