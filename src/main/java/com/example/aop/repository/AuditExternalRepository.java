package com.example.aop.repository;

import com.example.aop.model.AuditExternal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditExternalRepository extends JpaRepository<AuditExternal, Integer> {
}
