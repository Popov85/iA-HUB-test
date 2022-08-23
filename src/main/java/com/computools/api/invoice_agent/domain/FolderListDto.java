package com.computools.api.invoice_agent.domain;

import lombok.Data;

import java.util.List;

@Data
public class FolderListDto {
    private List<FolderDto> folderList;
}
