package com.example.service;

import com.example.entity.Department;
import com.example.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public List<Department> findAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department findDepartmentById(Long id){
        return departmentRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Department not foud for the id "+id)
        );
    }

    public void deleteDepartmentById(Long id){
        departmentRepository.deleteById(id);
        //Another method to delete department
        /*Department department=findDepartmentById(id);
        departmentRepository.delete(department);*/
    }

    public Department updateDepartmentById(Long id,Department department){
        Department existingDepartment=findDepartmentById(id);
        existingDepartment.setDepartmentName(department.getDepartmentName());
        existingDepartment.setDepartmentLocation(department.getDepartmentLocation());

        return departmentRepository.save(existingDepartment);
    }

}
