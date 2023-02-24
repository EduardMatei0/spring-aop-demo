package com.example.aop.json;

import com.example.aop.model.AzureGroups;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AzureGroupsDTO {

    private String name;
    private Integer id;

    public AzureGroupsDTO(AzureGroups azureGroups) {
        this.name = azureGroups.getName();
        this.id = azureGroups.getId();
    }
}
