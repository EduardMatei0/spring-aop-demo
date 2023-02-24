package com.example.aop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "audit_external")
@Getter
@Setter
public class AuditExternal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_external_id_seq")
    @SequenceGenerator(name = "audit_external_id_seq", sequenceName = "audit_external_id_seq", allocationSize = 1)
    private Integer id;
    private String methodName;
    private String reqBody;
    private String response;
    private Integer statusCode;
}
