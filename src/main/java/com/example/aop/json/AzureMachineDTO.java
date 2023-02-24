package com.example.aop.json;

import com.example.aop.model.AzureVirtualMachines;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AzureMachineDTO {

    private String name;
    private Integer size;
    private Integer memory;
    private String operatingSystem;
    private String uuid;
    private Integer id;
    private Integer groupId;

    public AzureMachineDTO(AzureVirtualMachines azureVirtualMachines) {
        this.id = azureVirtualMachines.getId();
        this.name = azureVirtualMachines.getName();
        this.size = azureVirtualMachines.getSize();
        this.memory = azureVirtualMachines.getMemory();
        this.operatingSystem = azureVirtualMachines.getOperatingSystem();
        this.uuid = azureVirtualMachines.getUuid();
        this.groupId = azureVirtualMachines.getAzureGroupId();
    }

}
