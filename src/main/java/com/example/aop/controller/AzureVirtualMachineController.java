package com.example.aop.controller;

import com.example.aop.json.AzureMachineDTO;
import com.example.aop.service.AzureVirtualMachinesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AzureVirtualMachineController {

    private final AzureVirtualMachinesService azureVirtualMachinesService;


    @GetMapping("vm")
    public ResponseEntity<List<AzureMachineDTO>> getAll(
            @RequestParam(value = "limit", defaultValue = "50", required = false) Integer limit,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseEntity.ok(azureVirtualMachinesService.getAll(page, limit));
    }

    @GetMapping("vm/{id}")
    public ResponseEntity<AzureMachineDTO> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(azureVirtualMachinesService.getOne(id));
    }

    @PostMapping("vm")
    public ResponseEntity<AzureMachineDTO> saveOne(@RequestBody AzureMachineDTO azureMachineDTO) {
        return ResponseEntity.ok(azureVirtualMachinesService.saveOne(azureMachineDTO));
    }

    @PutMapping("vm/{id}")
    public ResponseEntity<AzureMachineDTO> editOne(
            @RequestBody AzureMachineDTO azureMachineDTO,
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(azureVirtualMachinesService.editOne(azureMachineDTO, id));
    }

    @DeleteMapping("vm/{id}")
    public ResponseEntity<String> deleteOne(@PathVariable("id") Integer id) {
        azureVirtualMachinesService.deleteOne(id);
        return ResponseEntity.ok("Deleted");
    }
}
