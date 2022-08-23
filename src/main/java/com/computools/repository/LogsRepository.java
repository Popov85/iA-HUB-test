package com.computools.repository;

import com.computools.repository.table.Logs;

import java.util.Optional;

public interface LogsRepository extends CommonRepository<Logs> {
    void deleteById(String id);
    Logs update(Logs logs);
    Optional<Logs> findById(String id);
}
