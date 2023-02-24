package com.example.aop.json;

import com.example.aop.model.AzureGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AzureGroupsDTO {

    private String name;
    private Integer id;

    public AzureGroupsDTO(AzureGroups azureGroups) {
        this.name = azureGroups.getName();
        this.id = azureGroups.getId();
    }
}
