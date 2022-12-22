package org.api.services;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface GenericService<T> {
    Uni<T> findById(String id);

    Uni<List<T>> getAll();

//    Uni<T> update(Long id, T resource);

    Uni<T> save(T resource);

    Uni<Boolean> delete(String id);

}
