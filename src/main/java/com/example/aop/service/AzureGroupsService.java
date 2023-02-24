package com.example.aop.service;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.model.AzureGroups;
import com.example.aop.repository.AzureGroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AzureGroupsService {

    private final AzureGroupsRepository azureGroupsRepository;

    public List<AzureGroupsDTO> getAll(int page, int limit) {
        return azureGroupsRepository.findAll(PageRequest.of(page, limit))
                .stream()
                .map(AzureGroupsDTO::new)
                .collect(Collectors.toList());
    }

    public AzureGroupsDTO getOne(Integer id) {
        return azureGroupsRepository.findById(id)
                .map(AzureGroupsDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Azure group with id %s not present", id)));
    }

    public AzureGroupsDTO saveOne(AzureGroupsDTO azureGroupsDTO) {
        return new AzureGroupsDTO(azureGroupsRepository.save(convertFromDTO(azureGroupsDTO)));
    }

    public AzureGroupsDTO editOne(AzureGroupsDTO azureGroupsDTO, Integer id) {
        var azureGroup = azureGroupsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Azure group with id %s not present", id)));
        azureGroup.setName(azureGroupsDTO.getName());

        return new AzureGroupsDTO(azureGroupsRepository.save(azureGroup));
    }

    public void deleteOne(Integer id) {
        azureGroupsRepository.deleteById(id);
    }

    private AzureGroups convertFromDTO(AzureGroupsDTO azureGroupsDTO) {
        return AzureGroups.builder()
                .name(azureGroupsDTO.getName())
                .build();
    }
}
