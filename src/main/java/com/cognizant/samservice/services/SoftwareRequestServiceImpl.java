package com.cognizant.samservice.services;

import com.cognizant.samservice.exceptions.ResourceNotFoundException;
import com.cognizant.samservice.model.*;
import com.cognizant.samservice.model.enums.RequestStatus;
import com.cognizant.samservice.repository.SoftwareRequestRepository;
import com.cognizant.samservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SoftwareRequestServiceImpl implements SoftwareRequestService {

    private final SoftwareRequestRepository requestRepository;
    private final UserSoftwareAllocationService allocationService;
    private final UserRepository userRepository;

    @Autowired
    public SoftwareRequestServiceImpl(SoftwareRequestRepository requestRepository,
                                      UserSoftwareAllocationService allocationService,
                                      UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.allocationService = allocationService;
        this.userRepository = userRepository;
    }

    @Override
    public SoftwareRequestEntity createSoftwareRequest(SoftwareRequestEntity request) {
        request.setStatus(RequestStatus.PENDING);
        return requestRepository.save(request);
    }

    @Override
    public List<SoftwareRequestEntity> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    @Transactional
    public SoftwareRequestEntity respondToRequest(Long requestId, Long adminId, boolean accept, String adminResponse) {
        SoftwareRequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Software Request not found with id " + requestId));

        UserEntity adminUser = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found with id " + adminId));

        if (adminUser.getRole() != UserEntity.Role.ADMIN) {
            throw new RuntimeException("Only admins are allowed to accept or decline requests.");
        }

        if (accept) {
            // Accepting the request
            UserSoftwareAllocationEntity allocation = new UserSoftwareAllocationEntity();
            allocation.setUser(request.getUser());
            allocation.setSoftware(request.getSoftware());
            allocationService.createAllocation(allocation);

            request.setStatus(RequestStatus.ACCEPTED);
            request.setAdminResponse(adminResponse != null ? adminResponse : "Request accepted successfully.");
        } else {
            // Declining the request
            request.setStatus(RequestStatus.DECLINED);
            request.setAdminResponse(adminResponse != null ? adminResponse : "Request declined.");
        }

        return requestRepository.save(request);
    }
}
