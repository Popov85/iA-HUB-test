package com.computools.repository;

import java.util.List;

public interface CommonRepository<T> {
    T save(T table);
    List<T> findAll();
}
