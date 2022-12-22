package org.api.services;

import org.api.entities.Person;
import org.api.repositories.PersonRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PersonService implements GenericService<Person> {

    @Inject
    PersonRepository personRepository;

    public Uni<Long> count(){
        return personRepository.count();
    }

    @Override
    public Uni<Person> findById(String id) {
        return personRepository.findById(id);
    }

    @Override
    public Uni<List<Person>> getAll() {
        return personRepository.listAll();
    }

//    @Override
//    public Uni<Building> update(Long id, Building p) {
//        Uni<Building> building = findById(id);
//        building.onItem().ifNotNull().transform(entity -> {
//            entity.setBirth(p.getBirth());
//            entity.setName(p.getName());
//            entity.setLocation(p.getLocation());
//            return buildingRepository.persist(entity);
//
//        }).onItem().ifNotNull().transform();
//        return buildingRepository.update();
//    }

    @Override
    public Uni<Person> save(Person b) {
        return personRepository.persistAndFlush(b);
    }

    @Override
    public Uni<Boolean> delete(String id) {
        return personRepository.deleteById(id);
    }
}
