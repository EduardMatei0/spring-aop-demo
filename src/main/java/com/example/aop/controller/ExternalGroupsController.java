package com.example.aop.controller;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.service.FakeApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExternalGroupsController {

    private final FakeApiService fakeApiService;


    @GetMapping("/external/groups/{id}")
    public ResponseEntity<AzureGroupsDTO> getGroupForMachine(@PathVariable("id") String id) {
        return ResponseEntity.ok(fakeApiService.getMachineGroup(id));
    }
}
