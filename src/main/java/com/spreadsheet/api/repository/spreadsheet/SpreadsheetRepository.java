package com.spreadsheet.api.repository.spreadsheet;

import com.spreadsheet.api.model.spreadsheet.Spreadsheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpreadsheetRepository extends JpaRepository<Spreadsheet, Long> {

}
