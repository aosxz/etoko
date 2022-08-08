package com.etoko.etoko.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BuyerID", nullable = false)
    private Integer buyerId;

    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LastName", length = 20)
    private String lastName;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "BirthPlace", length = 20)
    private String birthPlace;

    @Column(name = "Address", length = 50)
    private String address;

    @OneToMany(mappedBy = "buyerId")
    private Set<Purchase> purchases = new LinkedHashSet<>();


    public Buyer(String firstName, String lastName, LocalDate birthDate, String birthPlace, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
    }
}