package com.computools.web.controller.iaHub;

import com.computools.service.iaHub.WebhooksService;
import com.computools.service.iaHub.dto.WebhooksDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/webhooks")
public class WebhooksController {
    @Autowired
    private WebhooksService webhooksService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<WebhooksDto> findAll() {
        List<WebhooksDto> all = webhooksService.findAll();
        log.debug("Find all = {}", all);
        return all;
    }

    @GetMapping(value = "/{webhookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public WebhooksDto findByHashKey(@PathVariable String webhookId) {
        log.debug("Find by hash key = {}", webhookId);
        Optional<WebhooksDto> byHashKey =
                webhooksService.findByHashKey(webhookId);
        return byHashKey.orElseThrow(() -> new RuntimeException("Item not found!"));
    }

    @GetMapping(value = "/{tenantNo}/{configurationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<WebhooksDto> findByCompositeIndex(@PathVariable Long tenantNo,
                                                  @PathVariable String configurationId) {
        log.debug("Find by composite index = {}, {}, {}", tenantNo, configurationId);
        return webhooksService.findByCompositeIndex(tenantNo, configurationId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public WebhooksDto save(@RequestBody @Valid WebhooksDto dto) {
        WebhooksDto savedWebhook = webhooksService.save(dto);
        log.debug("Saved config = {}", savedWebhook);
        return savedWebhook;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public WebhooksDto update(@RequestBody @Valid WebhooksDto dto) {
        WebhooksDto updatedWebhook = webhooksService.save(dto);
        log.debug("Update updatedWebhook = {}", updatedWebhook);
        return updatedWebhook;
    }

    @DeleteMapping("/{webhookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByHashKey(@PathVariable String webhookId) {
        log.debug("Delete by hash key = {}", webhookId);
        webhooksService.deleteByHashKey(webhookId);
        return;
    }

}
