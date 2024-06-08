package com.spreadsheet.api.repository.spreadsheet;

import com.spreadsheet.api.model.spreadsheet.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Long> {

}
