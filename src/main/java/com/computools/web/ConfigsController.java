package com.computools.web;

import com.computools.repository.ConfigsRepository;
import com.computools.repository.table.Configs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/configs")
public class ConfigsController {
    @Autowired
    private ConfigsRepository configsRepository;

    @GetMapping("/")
    public List<Configs> getAll() {
        List<Configs> all = configsRepository.findAll();
        log.debug("Find all = {}", all);
        return all;
    }

    @GetMapping("/{id}")
    public Configs getById(@PathVariable String id) {
        log.debug("Find by id = {}", id);
        Optional<Configs> all = configsRepository.findById(id);
        return all.orElse(null);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Configs save(@RequestBody Configs newConfig) {
        log.debug("New config = {}", newConfig);
        Configs savedConfig = configsRepository.save(newConfig);
        log.debug("Saved config = {}", savedConfig);
        return savedConfig;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        log.debug("Delete by id = {}", id);
        configsRepository.deleteById(id);
        return;
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Configs updateById(@RequestBody Configs newConfig) {
        log.debug("Update by id = {}", newConfig.getConfigId());
        Configs updatedConfig = configsRepository.updateById(newConfig.getConfigId(), newConfig);
        return updatedConfig;
    }

}
