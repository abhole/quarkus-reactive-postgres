package org.api.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonPropertyOrder(value = {"personId", "name","birth","location"})
@Data
public class PersonDto {

    private String personId;

    @NotBlank
    @Size(max = 50)
    public String name;

    @NotNull
    public LocalDate birth;

    @NotBlank
    @Size(max = 50)
    public String location;

}
