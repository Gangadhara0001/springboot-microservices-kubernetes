package com.example.feign;

import com.example.config.FeignConfig;
import com.example.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "department-service",
        //url="${department.baseUrl}",
        configuration = FeignConfig.class
)
public interface DepartmentFeignClient {
    // CREATE
    @PostMapping("/api/department")
    DepartmentDTO createDepartment(
            @RequestBody DepartmentDTO departmentDTO
    );

    // GET ALL
    @GetMapping("/api/department")
    List<DepartmentDTO> findAllDepartments();

    // GET BY ID
    @GetMapping("/api/department/{id}")
    DepartmentDTO findDepartmentById(
            @PathVariable Long id
    );

    // UPDATE
    @PutMapping("/api/department/{id}")
    DepartmentDTO updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentDTO departmentDTO
    );

    // DELETE
    @DeleteMapping("/api/department/{id}")
    String deleteDepartmentById(
            @PathVariable Long id
    );

}
