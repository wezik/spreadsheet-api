package com.spreadsheet.api.model.spreadsheet;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cells")
public class Cell {

    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Sheet sheet;
    private String column_name;
    private Integer row_number;
    private String formula;
    private String calculatedFormula;

}
