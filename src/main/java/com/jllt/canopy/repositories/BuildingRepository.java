package com.jllt.canopy.repositories;

import com.jllt.canopy.entities.Building;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Id;
import java.util.UUID;

@ApplicationScoped
public class BuildingRepository implements PanacheRepositoryBase<Building, String> {
}
