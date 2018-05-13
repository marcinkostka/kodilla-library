package com.crud.kodillalibrary.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class RentalDto {
    private Long id;
    private Long readerId;
    private Long bookCopyId;
    private LocalDate rentDate;
    private LocalDate returnDate;

    /*public RentalDto() {
    }

    public RentalDto(Long id, Long readerId, Long bookCopyId, LocalDate rentDate, LocalDate returnDate) {
        this.id = id;
        this.readerId = readerId;
        this.bookCopyId = bookCopyId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public Long getReaderId() {
        return readerId;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }*/
}