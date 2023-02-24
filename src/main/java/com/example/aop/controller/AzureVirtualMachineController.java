package com.example.aop.controller;

import com.example.aop.json.AzureMachineDTO;
import com.example.aop.service.AzureMachinesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AzureVirtualMachineController {

    private final AzureMachinesService azureMachinesService;


    @GetMapping("vm")
    public ResponseEntity<List<AzureMachineDTO>> getAll(
            @RequestParam(value = "limit", defaultValue = "50", required = false) Integer limit,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseEntity.ok(azureMachinesService.getAll(page, limit));
    }

    @GetMapping("vm/{id}")
    public ResponseEntity<AzureMachineDTO> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(azureMachinesService.getOne(id));
    }

    @PostMapping("vm")
    public ResponseEntity<AzureMachineDTO> saveOne(@RequestBody AzureMachineDTO azureMachineDTO) {
        return ResponseEntity.ok(azureMachinesService.saveOne(azureMachineDTO));
    }

    @PutMapping("vm/{id}")
    public ResponseEntity<AzureMachineDTO> editOne(
            @RequestBody AzureMachineDTO azureMachineDTO,
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(azureMachinesService.editOne(azureMachineDTO, id));
    }

    @DeleteMapping("vm/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable("id") Integer id) {
        azureMachinesService.deleteOne(id);
        return ResponseEntity.ok("Deleted");
    }
}
