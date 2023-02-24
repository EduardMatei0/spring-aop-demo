package com.example.aop.service;

import com.example.aop.json.AzureMachineDTO;
import com.example.aop.model.AzureVirtualMachines;
import com.example.aop.repository.AzureVirtualMachinesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AzureMachinesService {

    private final AzureVirtualMachinesRepository azureVirtualMachinesRepository;
    private final ExternalApiService externalApiService;

    public List<AzureMachineDTO> getAll(int page, int limit) {
        return azureVirtualMachinesRepository.findAll(PageRequest.of(page, limit))
                .stream()
                .map(AzureMachineDTO::new)
                .collect(Collectors.toList());
    }

    public AzureMachineDTO getOne(Integer id) {
        return azureVirtualMachinesRepository.findById(id)
                .map(AzureMachineDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Azure machine with id %s not present", id)));
    }

    public AzureMachineDTO saveOne(AzureMachineDTO azureMachineDTO) {
        return new AzureMachineDTO(azureVirtualMachinesRepository.save(convertFromDTO(azureMachineDTO)));
    }

    public AzureMachineDTO editOne(AzureMachineDTO azureMachineDTO, Integer id) {
        var virtualMachine = azureVirtualMachinesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Azure machine with id %s not present", id)));
        virtualMachine.setAzureGroupId(externalApiService.callExternalApiAndGetGroups(id).getId());
        virtualMachine.setMemory(azureMachineDTO.getMemory());
        virtualMachine.setName(azureMachineDTO.getName());
        virtualMachine.setOperatingSystem(azureMachineDTO.getOperatingSystem());
        virtualMachine.setSize(azureMachineDTO.getSize());

        return new AzureMachineDTO(azureVirtualMachinesRepository.save(virtualMachine));
    }

    public void deleteOne(Integer id) {
        azureVirtualMachinesRepository.deleteById(id);
    }

    private AzureVirtualMachines convertFromDTO(AzureMachineDTO azureMachineDTO) {
        return AzureVirtualMachines.builder()
                .uuid(UUID.randomUUID().toString())
                .name(azureMachineDTO.getName())
                .size(azureMachineDTO.getSize())
                .memory(azureMachineDTO.getMemory())
                .operatingSystem(azureMachineDTO.getOperatingSystem())
                .build();
    }
}
