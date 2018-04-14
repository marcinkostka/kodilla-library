package com.crud.kodillalibrary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="readers")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "registered")
    private LocalDate registered;

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "reader",
            fetch = FetchType.LAZY
    )
    List<Rental> rentalList = new ArrayList<>();

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registered = LocalDate.now();
    }

    public Reader(Long id, String firstName, String lastName, LocalDate registered) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registered = registered;
    }
}