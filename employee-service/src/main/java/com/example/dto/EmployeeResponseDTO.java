package com.example.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Long id;

    private String name;

    private String email;

    private double salary;

    private DepartmentDTO department;

}
