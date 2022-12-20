package com.jllt.canopy.services;

import com.jllt.canopy.entities.Building;
import com.jllt.canopy.repositories.BuildingRepository;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.List;

@ApplicationScoped
public class BuildingService implements GenericService<Building> {

    @Inject
    BuildingRepository buildingRepository;

    public Uni<Long> count(){
        return buildingRepository.count();
    }

    @Override
    public Uni<Building> findById(String id) {
        return buildingRepository.findById(id);
    }

    @Override
    public Uni<List<Building>> getAll() {
        return buildingRepository.listAll();
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
    public Uni<Building> save(Building b) {
        return buildingRepository.persistAndFlush(b);
    }

    @Override
    public Uni<Boolean> delete(String id) {
        return buildingRepository.deleteById(id);
    }
}
