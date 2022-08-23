package com.computools.repository;

import java.util.List;
import java.util.Optional;

public interface CommonRepository<T> {
    T save(T table);
    //Optional<T> findById(String id);
    List<T> findAll();
//    void deleteById(String id);
//    T updateById(String id, T table);
}
