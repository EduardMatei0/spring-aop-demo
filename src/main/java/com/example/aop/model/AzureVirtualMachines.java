package com.example.aop.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "virtual_machines")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AzureVirtualMachines {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "virtual_machines_id_seq")
    @SequenceGenerator(name = "virtual_machines_id_seq", sequenceName = "virtual_machines_id_seq", allocationSize = 1)
    private Integer id;

    private String name;
    private Integer azureGroupId;
    private String uuid;
    private Integer size;
    private Integer memory;
    private String operatingSystem;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
