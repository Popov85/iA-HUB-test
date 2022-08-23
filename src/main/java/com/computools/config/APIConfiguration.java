package com.computools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class APIConfiguration {

    @Value("${invoice_agent.base_url}")
    private String invoiceAgentBaseUrl;

    public String INVOICE_AGENT_LOGIN;
    public String INVOICE_AGENT_SESSION;
    public String INVOICE_AGENT_FOLDER_LIST;
    public String INVOICE_AGENT_ARCHIVE_UPLOAD;

    @PostConstruct
    private void setUrls() {
        INVOICE_AGENT_LOGIN = invoiceAgentBaseUrl + "/auth/login";
        INVOICE_AGENT_SESSION = invoiceAgentBaseUrl + "/sessions_v2/current";
        INVOICE_AGENT_FOLDER_LIST = invoiceAgentBaseUrl + "/folders_v4/1/list";
        INVOICE_AGENT_ARCHIVE_UPLOAD = invoiceAgentBaseUrl + "/archives_v3";
    }
}

