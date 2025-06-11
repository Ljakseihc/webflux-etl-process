package com.epam.springboot.advanced.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("sport_name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Sport {

    @Id
    private Integer id;
    private String name;

    public Sport(String name) {
        this.name = name;
    }
}
