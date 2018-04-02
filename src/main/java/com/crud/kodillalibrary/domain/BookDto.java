package com.crud.kodillalibrary.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}
