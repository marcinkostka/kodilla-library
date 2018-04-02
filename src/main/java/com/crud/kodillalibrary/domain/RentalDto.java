package com.crud.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class RentalDto {
    private Long id;
    private LocalDate returnDate;
    private LocalDate rentDate;
}
