package com.example.aop.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "azure_groups")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AzureGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "azure_groups_id_seq")
    @SequenceGenerator(name = "azure_groups_id_seq", sequenceName = "azure_groups_id_seq", allocationSize = 1)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "azureGroups")
    private List<Users> users = new ArrayList<>();

}
