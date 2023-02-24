package com.example.aop.external;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.json.ResourceGroups;
import com.example.aop.json.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ExternalApiService {


    public AzureGroupsDTO createNewGroup(AzureGroupsDTO azureGroupsDTO) {
        log.info("Creating azure group from Azure Directory groups service...");

        return AzureGroupsDTO.builder()
                .id(1)
                .name("Azure Group")
                .build();
    }

    public UserDto createNewUser(UserDto userDto) {
        log.info("Creating user from external service (Azure Directory)......");
        return UserDto
                .builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public ResourceGroups createNewResource(ResourceGroups resourceGroups) {
        log.info("Creating resource group...");
        return ResourceGroups
                .builder()
                .created(LocalDateTime.now())
                .name(resourceGroups.getName())
                .uuid(UUID.randomUUID().toString())
                .build();
    }

}

