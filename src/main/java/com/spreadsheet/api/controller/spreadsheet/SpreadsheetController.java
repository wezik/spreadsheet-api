package com.spreadsheet.api.controller.spreadsheet;

import com.spreadsheet.api.service.spreadsheet.SpreadsheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/spreadsheet")
public class SpreadsheetController {

    private final SpreadsheetService spreadsheetService;

    @GetMapping
    public ResponseEntity<?> getCurrentUserSpreadsheets(@RequestParam(name = "id", required = false) Long id) {
        if (id == null) {
            return ResponseEntity.ok(spreadsheetService.getCurrentUserSpreadsheets());
        }
        var opt = spreadsheetService.getSpreadsheetOwnedByCurrentUser(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping
    public ResponseEntity<?> createSpreadsheetForCurrentUser(@RequestParam(name = "title", required = false) String title) {
        String finalTitle = title == null ? "New Spreadsheet" : title;
        var opt = spreadsheetService.createSpreadsheetForCurrentUser(finalTitle);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("sheet")
    public ResponseEntity<?> createSheetForCurrentUser(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "spreadsheetId") Long spreadsheetId) {
        var opt = spreadsheetService.createSheetForCurrentUser(title, spreadsheetId);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(opt.get());
    }

}
