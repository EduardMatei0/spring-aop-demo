package com.example.aop.service;

import com.example.aop.json.AzureGroupsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;

    public AzureGroupsDTO callExternalApiAndGetGroups(Integer id) {
        log.info("Calling external service to get groups ... ");
        try {
            ResponseEntity<AzureGroupsDTO> forEntity = restTemplate.getForEntity("http://localhost:8080/api/external/groups/" + id, AzureGroupsDTO.class);
            return forEntity.getBody();
        } catch (RestClientException e) {
            throw e;
        } finally {
            log.info("We are in the finally block, doing cleanup...");
        }
    }
}
