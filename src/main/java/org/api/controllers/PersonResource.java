package org.api.controllers;

import org.api.dto.PersonDto;
import org.api.entities.Person;
import org.api.mappers.PersonMapper;
import org.api.services.PersonService;
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

@Path("persons")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PersonResource {
    private static final Logger LOGGER = Logger.getLogger(PersonResource.class.getName());

    private final PersonService personService;
    private final PersonMapper personMapper;


    @Inject
    public PersonResource(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@PathParam("id") String id) {
        return personService.findById(id)
                .onItem().ifNotNull().transform(person -> ok(personMapper.personToPersonDto(person)).build())
                .onItem().ifNull().continueWith(status(NOT_FOUND)::build);
    }

    @GET
    public Uni<Response> getAll () {
        return personService.getAll()
                .onItem().transform(persons ->
                        {
                            final List<PersonDto> personDtoList = personMapper.personsToPersonsDto(persons);
                            return ok(personDtoList).build();
                        });
    }

//    @PUT
//    public Uni<Response> update(Long id, Person p) {
//        return personService.update(id,p)
//    }


    @POST
    public Uni<Response> create(PersonDto personDto) {
        final Person person = personMapper.personDtoToPerson(personDto);
        return personService.save(person)
                .onItem().ifNotNull().transform(p -> ok(personMapper.personToPersonDto(p)).build())
                .onItem().ifNull().continueWith(status(INTERNAL_SERVER_ERROR)::build);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteById(@PathParam("id") String id) {
        return personService.delete(id);
    }

}
