package com.cognizant.samservice.repository;

import com.cognizant.samservice.model.EmployeeProjectEntity;
import com.cognizant.samservice.model.UserEntity;
import com.cognizant.samservice.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProjectEntity, Long> {
    // Custom query to find EmployeeProjectEntity by User and Project
    Optional<EmployeeProjectEntity> findByEmployeeAndProject(UserEntity employee, ProjectEntity project);
}
