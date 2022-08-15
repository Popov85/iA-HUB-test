package com.computools.repository;

import com.computools.repository.table.Logs;

import java.util.List;
import java.util.Optional;

public interface LogsRepository {
    Logs save(Logs log);
    Optional<Logs> findById(String id);
    List<Logs> findAll();
    void deleteById(String id);
    Logs updateById(String id, Logs logs);
}
