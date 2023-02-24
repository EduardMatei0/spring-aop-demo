package com.example.aop.external;

import com.example.aop.json.AzureGroupsDTO;
import com.example.aop.json.ResourceGroups;
import com.example.aop.json.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalApiService externalApIService;


    @PostMapping("/external/groups")
    public ResponseEntity<AzureGroupsDTO> createNewGroup(@RequestBody AzureGroupsDTO azureGroupsDTO) {
        return ResponseEntity.ok(externalApIService.createNewGroup(azureGroupsDTO));
    }

    @PostMapping("/external/resource")
    public ResponseEntity<ResourceGroups> createNewResource(@RequestBody ResourceGroups resourceGroups) {
        return ResponseEntity.ok(externalApIService.createNewResource(resourceGroups));
    }

    @PostMapping("/external/users")
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(externalApIService.createNewUser(userDto));
    }
}
