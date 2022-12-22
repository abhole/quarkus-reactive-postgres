package org.api.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "person_id", nullable = false)
    private String personId;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String name;

    @Column(name = "birth", nullable = false)
    @NotNull
    public LocalDate birth;

    @Column(name = "location", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String location;
}
