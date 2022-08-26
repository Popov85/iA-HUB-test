package com.computools.api.invoiceAgent.domain;

import lombok.Data;

import java.util.List;

@Data
public class FolderListDto {
    private List<FolderDto> folderList;
}
