package com.spreadsheet.api.model.spreadsheet;

import com.spreadsheet.api.model.authentication.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "spreadsheets")
public class Spreadsheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    @OneToMany(mappedBy = "spreadsheet", fetch = FetchType.LAZY)
    private List<Sheet> sheets;

}
