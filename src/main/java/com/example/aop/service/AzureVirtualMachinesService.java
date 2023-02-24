package com.example.aop.service;

import com.example.aop.json.AzureMachineDTO;
import com.example.aop.json.ResourceGroups;
import com.example.aop.model.AzureVirtualMachines;
import com.example.aop.repository.AzureVirtualMachinesRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AzureVirtualMachinesService {

    private final AzureVirtualMachinesRepository azureVirtualMachinesRepository;
    private final ConnectorService connectorService;

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
        // fake calling external api
        connectorService.callAzureDirectoryServiceAndCreateResource(ResourceGroups
                .builder()
                .name(RandomString.make())
                .uuid(UUID.randomUUID().toString())
                .build());

        // saving new virtual machine
        return new AzureMachineDTO(azureVirtualMachinesRepository.save(convertFromDTO(azureMachineDTO)));
    }

    public AzureMachineDTO editOne(AzureMachineDTO azureMachineDTO, Integer id) {
        var virtualMachine = azureVirtualMachinesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Azure machine with id %s not present", id)));
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
