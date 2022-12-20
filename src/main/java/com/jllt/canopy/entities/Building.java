package com.jllt.canopy.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "building_id", nullable = false)
    private String buildingId;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String name;

    @Column(name = "opened", nullable = false)
    @NotNull
    public LocalDate opened;

    @Column(name = "location", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String location;
}
