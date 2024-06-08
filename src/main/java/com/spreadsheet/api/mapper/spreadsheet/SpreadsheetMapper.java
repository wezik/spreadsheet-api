package com.spreadsheet.api.mapper.spreadsheet;

import com.spreadsheet.api.dto.spreadsheet.SheetResponse;
import com.spreadsheet.api.dto.spreadsheet.SpreadsheetResponse;
import com.spreadsheet.api.model.spreadsheet.Sheet;
import com.spreadsheet.api.model.spreadsheet.Spreadsheet;
import org.springframework.stereotype.Service;

@Service
public class SpreadsheetMapper {

    public SpreadsheetResponse mapSpreadsheetToResponse(Spreadsheet spreadsheet) {
        var spreadsheetResponse = new SpreadsheetResponse();
        spreadsheetResponse.setId(spreadsheet.getId());
        spreadsheetResponse.setTitle(spreadsheet.getTitle());
        spreadsheetResponse.setSheets(spreadsheet.getSheets().stream().map(this::mapSheetToResponse).toList());
        return spreadsheetResponse;
    }

    public SheetResponse mapSheetToResponse(Sheet sheet) {
        var sheetResponse = new SheetResponse();
        sheetResponse.setId(sheet.getId());
        sheetResponse.setTitle(sheet.getTitle());
        return sheetResponse;
    }

}
