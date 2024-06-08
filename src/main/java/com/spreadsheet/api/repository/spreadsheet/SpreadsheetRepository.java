package com.spreadsheet.api.repository.spreadsheet;

import com.spreadsheet.api.model.spreadsheet.Spreadsheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpreadsheetRepository extends JpaRepository<Spreadsheet, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Spreadsheet s WHERE s.id = ?1 AND s.owner.username = ?2")
    boolean existsByIdAndOwner_Username(@Param("id") Long id, @Param("username") String username);
}
