package com.jllt.canopy.mappers;

import com.jllt.canopy.dto.BuildingDto;
import com.jllt.canopy.entities.Building;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = QuarkusMappingConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BuildingMapper {
    BuildingMapper INSTANCE = Mappers.getMapper(BuildingMapper.class);

    @Named("dtoToEntity")
    Building buildingDtoToBuilding(BuildingDto source);

    @Named("entityToDto")
    BuildingDto buildingToBuildingDto(Building source);

    @IterableMapping(qualifiedByName="entityToDto")
    List<BuildingDto> buildingsToBuildingsDto(List<Building> buildingList);

    @IterableMapping(qualifiedByName="dtoToEntity")
    List<Building> buildingDtosToBuildings(List<BuildingDto> buildingDtoList);
}
