package com.example.aop.repository;

import com.example.aop.model.AzureVirtualMachines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AzureVirtualMachinesRepository extends JpaRepository<AzureVirtualMachines, Integer> {
}
