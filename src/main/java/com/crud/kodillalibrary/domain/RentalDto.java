package com.crud.kodillalibrary.domain;

import java.time.LocalDate;

public class RentalDto {
    private Long id;
    private Long readerId;
    private Long bookCopyId;
    private int yearRent;
    private int monthRent;
    private int dayRent;
    private LocalDate returnDate;

    public RentalDto() {
    }

    public RentalDto(Long id, Long readerId, Long bookCopyId, LocalDate rentDate, LocalDate returnDate) {
        this.id = id;
        this.readerId = readerId;
        this.bookCopyId = bookCopyId;
        this.yearRent = rentDate.getYear();
        this.monthRent = rentDate.getMonthValue();
        this.dayRent = rentDate.getDayOfMonth();
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
        return LocalDate.of(yearRent, monthRent, dayRent);
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}