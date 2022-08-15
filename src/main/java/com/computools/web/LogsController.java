package com.computools.web;

import com.computools.repository.table.Logs;
import com.computools.repository.LogsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/logs")
public class LogsController {
    @Autowired
    private LogsRepository logsRepository;

    @GetMapping("/")
    public List<Logs> getAll() {
        List<Logs> all = logsRepository.findAll();
        log.debug("Find all = {}", all);
        return all;
    }

    @GetMapping("/{id}")
    public Logs getById(@PathVariable String id) {
        log.debug("Find by id = {}", id);
        Optional<Logs> allLogs = logsRepository.findById(id);
        return allLogs.orElse(null);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Logs save(@RequestBody Logs newLog) {
        log.debug("New log = {}", newLog);
        Logs savedLog = logsRepository.save(newLog);
        log.debug("Saved log = {}", savedLog);
        return savedLog;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        log.debug("Delete by id = {}", id);
        logsRepository.deleteById(id);
        return;
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Logs updateById(@RequestBody Logs newLog) {
        log.debug("Update by id = {}", newLog.getAgreementId());
        Logs updatedLog = logsRepository.updateById(newLog.getAgreementId(), newLog);
        return updatedLog;
    }

}
