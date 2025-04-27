package com.cognizant.samservice.controller;

import com.cognizant.samservice.model.UserSoftwareAllocationEntity;
import com.cognizant.samservice.services.UserSoftwareAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/software/allocate")
public class UserSoftwareAllocationController
{
    @Autowired
    private UserSoftwareAllocationService allocationService;

    // Create a new allocation
    @PostMapping("/create")
    public UserSoftwareAllocationEntity createAllocation(@RequestBody UserSoftwareAllocationEntity allocation) {
        return allocationService.createAllocation(allocation);
    }

    // Get allocation by id
    @GetMapping("/searchAllocation")
    public UserSoftwareAllocationEntity getAllocationById(@RequestParam Long id) {
        return allocationService.getAllocationById(id);
    }

    // Get all allocations
    @GetMapping("/getall")
    public List<UserSoftwareAllocationEntity> getAllAllocations() {
        return allocationService.getAllAllocations();
    }

    // Delete an allocation
    @DeleteMapping("/{id}")
    public void deleteAllocation(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
    }
}
