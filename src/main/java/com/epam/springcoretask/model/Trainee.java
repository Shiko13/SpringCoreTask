package com.epam.springcoretask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Trainee {

    private Long id;

    private LocalDate dateOfBirth;

    private String address;

    private Long userId;

    public Trainee(LocalDate dateOfBirth, String address, Long userId) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
    }

    public Trainee(String address, Long userId) {
        this.address = address;
        this.userId = userId;
    }

    public Trainee(LocalDate dateOfBirth, Long userId) {
        this.dateOfBirth = dateOfBirth;
        this.userId = userId;
    }

    public Trainee(Long userId) {
        this.userId = userId;
    }
}
