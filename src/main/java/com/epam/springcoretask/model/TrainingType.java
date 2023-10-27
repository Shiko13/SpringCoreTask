package com.epam.springcoretask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TrainingType {

    private Long id;

    private String name;

    private static Long idCounter = 1L;

    public TrainingType(String name) {
        this.id = generateUniqueId();
        this.name = name;
    }

    private Long generateUniqueId() {
        return idCounter++;
    }

}
