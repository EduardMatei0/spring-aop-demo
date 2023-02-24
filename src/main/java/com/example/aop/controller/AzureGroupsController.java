package com.example.aop.controller;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.json.AzureMachineDTO;
import com.example.aop.service.AzureGroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AzureGroupsController {
    
    private final AzureGroupsService azureGroupsService;

    @GetMapping("groups")
    public ResponseEntity<List<AzureGroupsDTO>> getAll(
            @RequestParam(value = "limit", defaultValue = "50", required = false) Integer limit,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseEntity.ok(azureGroupsService.getAll(page, limit));
    }

    @GetMapping("groups/{id}")
    public ResponseEntity<AzureGroupsDTO> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(azureGroupsService.getOne(id));
    }

    @PostMapping("groups")
    public ResponseEntity<AzureGroupsDTO> saveOne(@RequestBody AzureGroupsDTO azureGroupsDTO) {
        return ResponseEntity.ok(azureGroupsService.saveOne(azureGroupsDTO));
    }

    @PutMapping("groups/{id}")
    public ResponseEntity<AzureGroupsDTO> editOne(
            @RequestBody AzureGroupsDTO azureGroupsDTO,
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(azureGroupsService.editOne(azureGroupsDTO, id));
    }

    @DeleteMapping("groups/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable("id") Integer id) {
        azureGroupsService.deleteOne(id);
        return ResponseEntity.ok("Deleted");
    }
}
