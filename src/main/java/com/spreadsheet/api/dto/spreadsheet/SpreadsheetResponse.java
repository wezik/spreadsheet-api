package com.spreadsheet.api.dto.spreadsheet;

import lombok.Data;

import java.util.List;

@Data
public class SpreadsheetResponse {
    private Long id;
    private String title;
    private List<SheetResponse> Sheets;
}
