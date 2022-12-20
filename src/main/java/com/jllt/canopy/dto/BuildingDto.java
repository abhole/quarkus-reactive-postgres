package com.jllt.canopy.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonPropertyOrder(value = {"buildingId", "name","opened","location"})
@Data
public class BuildingDto {

    private String buildingId;

    @NotBlank
    @Size(max = 50)
    public String name;

    @NotNull
    public LocalDate opened;

    @NotBlank
    @Size(max = 50)
    public String location;

}
