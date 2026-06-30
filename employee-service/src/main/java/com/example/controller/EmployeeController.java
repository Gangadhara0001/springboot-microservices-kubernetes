package com.example.controller;

import com.example.dto.DepartmentDTO;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping
  //  @PreAuthorize("hasRole('ADMIN')")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        logger.info("Received request to create employee: {}", employee.getEmail());
        return employeeService.createEmployee(employee);
    }

    @GetMapping
  //  @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Employee> findAllEmployees(){
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Employee findEmployeeById(@PathVariable Long id){
        logger.info("Fetching employee with id: {}", id);
        return employeeService.findEmployeeById(id);
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployeeById(@PathVariable Long id){
        logger.warn("Deleting employee with id: {}", id);
        employeeService.deleteEmployeeById(id);
        return "Successfully deleted employee for the id "+id;
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(@PathVariable Long id,@RequestBody Employee employee){
        return employeeService.updateEmployeeById(id,employee);
    }

    @PostMapping("/department")
    public DepartmentDTO createDepartment(
            @RequestBody DepartmentDTO departmentDTO){

        return employeeService.createDepartment(departmentDTO);
    }

    @GetMapping("/department")
    public List<DepartmentDTO> findAllDepartments(){
        logger.info("Calling Department Service");
        return employeeService.findAllDepartments();
    }

    @GetMapping("/department/{id}")
    public DepartmentDTO findDepartmentById(@PathVariable Long id){

        return employeeService.findDepartmentById(id);
    }

    /*@PutMapping("/department/{id}")
    public DepartmentDTO createDepartment(@PathVariable Long id,
            @RequestBody DepartmentDTO departmentDTO){

        return employeeService.updateDepartment(id,departmentDTO);
    }
    @DeleteMapping("/department/{id}")
    public String deleteDepartmentById(@PathVariable Long id){
        employeeService.deleteDepartmentById(id);
        return "Successfully deleted the department for the id "+id;
    }*/

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");

    }

}
