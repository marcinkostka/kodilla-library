package com.crud.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookCopyDto {
    private Long id;
    private Long bookId;
    private BookStatuses status;
}
