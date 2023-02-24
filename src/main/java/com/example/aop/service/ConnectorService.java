package com.example.aop.service;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.json.ResourceGroups;
import com.example.aop.json.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectorService {

    private final RestTemplate restTemplate;

    public AzureGroupsDTO callAzureDirectoryServiceAndCreateGroup(AzureGroupsDTO azureGroupsDTO) {
        log.info("Calling external service to create group ... ");
        try {
            Thread.sleep(1000);
            ResponseEntity<AzureGroupsDTO> forEntity = restTemplate.postForEntity("http://localhost:8080/api/external/groups", azureGroupsDTO, AzureGroupsDTO.class);
            return forEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw e;
        } catch (InterruptedException e) {
            log.info("Interrupted exception {}", e.getMessage());
            return null;
        }
    }

    public ResourceGroups callAzureDirectoryServiceAndCreateResource(ResourceGroups resourceGroups) {
        log.info("Calling external service to create resource ... ");
        try {
            Thread.sleep(1000);
            ResponseEntity<ResourceGroups> forEntity = restTemplate.postForEntity("http://localhost:8080/api/external/resource", resourceGroups, ResourceGroups.class);
            return forEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw e;
        } catch (InterruptedException e) {
            log.info("Interrupted exception {}", e.getMessage());
            return null;
        }
    }

    public UserDto callMicrosoftAccountsServiceAndCreateUsers(UserDto userDto) {
        log.info("Calling external service to create users ... ");
        try {
            Thread.sleep(1000);
            ResponseEntity<UserDto> forEntity = restTemplate.postForEntity("http://localhost:8080/api/external/users", userDto, UserDto.class);
            return forEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw e;
        } catch (InterruptedException e) {
            log.info("Interrupted exception {}", e.getMessage());
            return null;
        }
    }
}
