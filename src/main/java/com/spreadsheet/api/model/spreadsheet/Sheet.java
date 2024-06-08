package com.spreadsheet.api.model.spreadsheet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sheets")
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spreadsheet spreadsheet;
    @OneToMany(mappedBy = "sheet", fetch = FetchType.LAZY)
    private List<Cell> cells;


}
