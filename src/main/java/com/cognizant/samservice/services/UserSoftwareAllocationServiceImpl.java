package com.cognizant.samservice.services;

import com.cognizant.samservice.exceptions.AllocationNotFoundException;
import com.cognizant.samservice.model.SoftwareEntity;
import com.cognizant.samservice.model.UserSoftwareAllocationEntity;
import com.cognizant.samservice.repository.SoftwareRepository;
import com.cognizant.samservice.repository.UserRepository;
import com.cognizant.samservice.repository.UserSoftwareAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSoftwareAllocationServiceImpl implements UserSoftwareAllocationService {

    private final UserSoftwareAllocationRepository allocationRepository;
    private final UserRepository userRepository;
    private final SoftwareRepository softwareRepository;

    @Autowired
    public UserSoftwareAllocationServiceImpl(UserSoftwareAllocationRepository allocationRepository,
                                             UserRepository userRepository,
                                             SoftwareRepository softwareRepository) {
        this.allocationRepository = allocationRepository;
        this.userRepository = userRepository;
        this.softwareRepository = softwareRepository;
    }

    @Override
    @Transactional
    public UserSoftwareAllocationEntity createAllocation(UserSoftwareAllocationEntity allocation) {
        // Fetch the software associated with the allocation
        SoftwareEntity software = allocation.getSoftware(); // Assuming allocation has a reference to SoftwareEntity

        if (software != null) {
            // Fetch latest software from DB to avoid stale data
            SoftwareEntity existingSoftware = softwareRepository.findById(software.getId())
                    .orElseThrow(() -> new RuntimeException("Software not found with id: " + software.getId()));

            // Increment activeUser count
            existingSoftware.setActiveUser(existingSoftware.getActiveUser() + 1);

            // Save updated software
            softwareRepository.save(existingSoftware);
        }

        // Save the allocation
        return allocationRepository.save(allocation);
    }

    @Override
    public UserSoftwareAllocationEntity getAllocationById(Long id) {
        return allocationRepository.findById(id)
                .orElseThrow(() -> new AllocationNotFoundException("Allocation not found with id " + id));
    }

    @Override
    public List<UserSoftwareAllocationEntity> getAllAllocations() {
        return allocationRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAllocation(Long id) {
        if (!allocationRepository.existsById(id)) {
            throw new AllocationNotFoundException("Allocation not found with id " + id);
        }
        allocationRepository.deleteById(id);
    }
}
