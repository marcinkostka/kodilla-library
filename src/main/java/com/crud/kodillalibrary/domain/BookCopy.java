package com.crud.kodillalibrary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "BookCopy.getCountOfBookCopiesByBookTitleAndAvailable",
        query = "SELECT count(*)" +
                " FROM books b, books_copy bc" +
                " WHERE b.id = bc.book_id" +
                " AND b.title = :TITLE" +
                " AND bc.status = 'AVAILABLE'"
)

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "booksCopy")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatuses status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "bookCopy",
            fetch = FetchType.LAZY
    )
    List<Rental> rentalList = new ArrayList<>();

    public BookCopy(BookStatuses status) {
        this.status = status;
    }

    public BookCopy(Long id, Book book, BookStatuses status) {
        this.id = id;
        this.book = book;
        this.status = status;
    }
}