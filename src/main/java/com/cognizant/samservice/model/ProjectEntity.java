package com.cognizant.samservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "project")
    private List<UserSoftwareAllocationEntity> softwareAllocations; // Allocated software for the project

    @OneToMany(mappedBy = "project")
    private List<EmployeeProjectEntity> employeeProjects;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<SoftwareEntity> softwares;  // Software used by the project// Employees working on the project
}
