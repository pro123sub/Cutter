package com.cognizant.samservice.model;

import com.cognizant.samservice.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "software_request")
public class SoftwareRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;  // The user who is requesting

    @ManyToOne
    @JoinColumn(name = "software_id", nullable = false)
    private SoftwareEntity software;  // Requested software

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;  // Enum instead of String ✅

    private String adminResponse; // Admin comments
}
