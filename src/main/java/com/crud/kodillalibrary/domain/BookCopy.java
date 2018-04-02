package com.crud.kodillalibrary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "booksCopy")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "status")
    private String status;

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

    public BookCopy(String status) {
        this.status = status;
    }
}