package org.api.mappers;

import org.api.dto.PersonDto;
import org.api.entities.Person;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Named("dtoToEntity")
    Person personDtoToPerson(PersonDto source);

    @Named("entityToDto")
    PersonDto personToPersonDto(Person source);

    @IterableMapping(qualifiedByName="entityToDto")
    List<PersonDto> personsToPersonsDto(List<Person> personList);

    @IterableMapping(qualifiedByName="dtoToEntity")
    List<Person> personDtosToPersons(List<PersonDto> personDtoList);
}
