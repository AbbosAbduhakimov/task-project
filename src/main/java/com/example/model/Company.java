package com.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "block", columnDefinition = "boolean default false")
    private boolean block;

    @OneToMany(mappedBy = "company",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();


}