package com.jllt.canopy.controllers;

import com.jllt.canopy.dto.BuildingDto;
import com.jllt.canopy.entities.Building;
import com.jllt.canopy.mappers.BuildingMapper;
import com.jllt.canopy.services.BuildingService;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Path("buildings")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class BuildingResource {
    private static final Logger LOGGER = Logger.getLogger(BuildingResource.class.getName());

    private final BuildingService buildingService;
    private final BuildingMapper buildingMapper;


    @Inject
    public BuildingResource(BuildingService buildingService, BuildingMapper buildingMapper) {
        this.buildingService=buildingService;
        this.buildingMapper = buildingMapper;
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@PathParam("id") String id) {
        return buildingService.findById(id)
                .onItem().ifNotNull().transform(building -> ok(buildingMapper.buildingToBuildingDto(building)).build())
                .onItem().ifNull().continueWith(status(NOT_FOUND)::build);
    }

    @GET
    public Uni<Response> getAll () {
        return buildingService.getAll()
                .onItem().transform(buildings ->
                        {
                            final List<BuildingDto> buildingDtoList = buildingMapper.buildingsToBuildingsDto(buildings);
                            return ok(buildingDtoList).build();
                        });
    }

//    @PUT
//    public Uni<Response> update(Long id, Building p) {
//        return buildingService.update(id,p)
//    }


    @POST
    public Uni<Response> create(BuildingDto buildingDto) {
        final Building building = buildingMapper.buildingDtoToBuilding(buildingDto);
        return buildingService.save(building)
                .onItem().ifNotNull().transform(p -> ok(buildingMapper.buildingToBuildingDto(p)).build())
                .onItem().ifNull().continueWith(status(INTERNAL_SERVER_ERROR)::build);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteById(@PathParam("id") String id) {
        return buildingService.delete(id);
    }

}
