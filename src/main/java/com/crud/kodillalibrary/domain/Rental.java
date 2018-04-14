package com.crud.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "readerId")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "bookCopyId")
    private BookCopy bookCopy;

    @Column(name = "rentDate")
    private LocalDate rentDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    public Rental() {
        this.rentDate = LocalDate.now();
        this.returnDate = null;
    }

    public Rental(Long id, Reader reader, BookCopy bookCopy) {
        this.id = id;
        this.reader = reader;
        this.bookCopy = bookCopy;
        this.rentDate = LocalDate.now();
        this.returnDate = null;
    }
    public void resetReturnDate() {
        this.returnDate = LocalDate.now();
    }
}