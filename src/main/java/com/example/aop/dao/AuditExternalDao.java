package com.example.aop.dao;

import com.example.aop.model.AuditExternal;
import com.example.aop.repository.AuditExternalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AuditExternalDao {

    private final AuditExternalRepository auditExternalRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveToDb(AuditExternal auditExternal) {
        auditExternalRepository.save(auditExternal);
    }
}
