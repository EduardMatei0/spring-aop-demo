package com.example.aop.service;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.json.UserDto;
import com.example.aop.model.AzureGroups;
import com.example.aop.repository.AzureGroupsRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AzureGroupsService {

    private final AzureGroupsRepository azureGroupsRepository;
    private final ConnectorService connectorService;

    @Transactional
    public List<AzureGroupsDTO> getAll(int page, int limit) {
        return azureGroupsRepository.findAll(PageRequest.of(page, limit))
                .stream()
                .map(AzureGroupsDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AzureGroupsDTO getOne(Integer id) {
        return azureGroupsRepository.findById(id)
                .map(AzureGroupsDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Azure group with id %s not present", id)));
    }

    @Transactional
    public AzureGroupsDTO saveOne(AzureGroupsDTO azureGroupsDTO) {

        // fake calling external api to create groups
        connectorService.callAzureDirectoryServiceAndCreateGroup(azureGroupsDTO);

        // fake calling external api to create users
        connectorService.callMicrosoftAccountsServiceAndCreateUsers(UserDto
                .builder()
                .email(RandomString.make() + "@gmail.com")
                .name(RandomString.make())
                .build());

        return new AzureGroupsDTO(azureGroupsRepository.save(convertFromDTO(azureGroupsDTO)));
    }

    @Transactional
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
