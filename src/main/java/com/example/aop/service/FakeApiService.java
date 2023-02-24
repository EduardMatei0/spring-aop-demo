package com.example.aop.service;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.repository.AzureGroupsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FakeApiService {

    private final AzureGroupsRepository azureGroupsRepository;

    public AzureGroupsDTO getMachineGroup(String uuid) {
        log.info("Getting azure group for machine id ... {}", uuid);

        return azureGroupsRepository.findById(1)
                .map(AzureGroupsDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Azure group not found with id 1"));
    }
}
