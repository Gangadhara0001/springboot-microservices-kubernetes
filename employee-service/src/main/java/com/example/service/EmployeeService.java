package com.example.service;

import com.example.dto.DepartmentDTO;
import com.example.entity.Employee;
import com.example.exception.ResourceNotFoundException;
import com.example.feign.DepartmentFeignClient;
//import com.example.helper.ApiClientHelper;
import com.example.helper.RestTemplteHelper;
import com.example.repository.EmployeeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
//import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    /*private static final Logger logger= LoggerFactory.getLogger(EmployeeService.class); Instead of this I am using import lombok.extern.slf4j.Slf4j;*/

    private  final EmployeeRepository employeeRepository;
   // private final ApiClientHelper apiClientHelper;
    private final RestTemplteHelper restTemplteHelper;
    private final DepartmentFeignClient departmentFeignClient;

    @Value("${department.baseUrl}")
    private String departmentBaseUrl;

    public EmployeeService(EmployeeRepository employeeRepository, /*ApiClientHelper apiClientHelper,*/ RestTemplteHelper restTemplteHelper, DepartmentFeignClient departmentFeignClient){
        this.employeeRepository=employeeRepository;
        //this.apiClientHelper = apiClientHelper;
        this.restTemplteHelper = restTemplteHelper;
        this.departmentFeignClient = departmentFeignClient;
    }

    public Employee createEmployee(Employee employee){
        log.info("Creating employee with email: {}", employee.getEmail());
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created successfully with id: {}",
                savedEmployee.getId());
        return savedEmployee;
    }

    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id){
        log.info("Fetching employee from DB with id: {}", id);
        return employeeRepository.findById(id).orElseThrow(()->
                 new ResourceNotFoundException("Employee not found with id: " + id)
        );
    }

    public void deleteEmployeeById(Long id){
        log.warn("Deleting employee with id: {}", id);
        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully");
        //another method to delete an employee
        /*Employee employee=findEmployeeById(id);
        employeeRepository.delete(employee);*/
    }

    public Employee updateEmployeeById(Long id, Employee employee){
        Employee existingEmployee=findEmployeeById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setSalary(employee.getSalary());
        return employeeRepository.save(existingEmployee);
    }

    /*public DepartmentDTO createDepartment(DepartmentDTO departmentDTO){
        return apiClientHelper.post(buildUriFormForPostDepartment(),departmentDTO, DepartmentDTO.class)
                .doOnError(
                        error->System.out.println(error.getMessage())
                )
                .onErrorResume(error->{
                    DepartmentDTO failedDto =
                            new DepartmentDTO();

                    failedDto.setDepartmentName(
                            "Department creation failed");
                    return  Mono.just(failedDto);
                })
                .block();
    }*/

    private String buildUriFormForPostDepartment(){
        return UriComponentsBuilder
                .fromUriString(departmentBaseUrl)
                .path("/api/department")
                .toUriString();
    }

    /*public List<DepartmentDTO> findAllDepartments() {
        return apiClientHelper.getList(buildUriFromForGetAllDepartment(),
                        new ParameterizedTypeReference<List<DepartmentDTO>>(){})
                .doOnError(
                        error->System.out.println(error.getMessage())
                        )
                .onErrorResume(
                        error->{
                           return Mono.just(Collections.emptyList());
                        })
                .block();
    }*/
    private String buildUriFromForGetAllDepartment(){
        return UriComponentsBuilder
                .fromUriString(departmentBaseUrl)
                .path("/api/department")
                .toUriString();
    }

    /*public DepartmentDTO findDepartmentById(Long id) {
        return apiClientHelper
                .get(buildUriFromForGetDepartmentById(id), DepartmentDTO.class)
                .doOnError(
                        error-> System.out.println(error.getMessage())
                )
                .onErrorResume(
                        error->{
                            DepartmentDTO failedDto =
                                    new DepartmentDTO();

                            failedDto.setDepartmentName(
                                    "department not found for the id: " + id);
                            return Mono.just(failedDto);
                        }
                )
                .block();
    }*/

    private String buildUriFromForGetDepartmentById(Long id){
        return UriComponentsBuilder
                .fromUriString(departmentBaseUrl)
                .path("/api/department/"+id)
                .toUriString();
    }

    /*public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        return apiClientHelper.update(buildUriFromForUpdateDepartment(id),departmentDTO,
                DepartmentDTO.class)
                .doOnError(
                        error-> System.out.println(error.getMessage())
                )
                .onErrorResume(error-> {
                    DepartmentDTO failedDto = new DepartmentDTO();
                    failedDto.setDepartmentName("failed to update the department for the id: "+id);
                    return Mono.just(failedDto);
                })
                .block();
    }

    private String buildUriFromForUpdateDepartment(Long id){
        return UriComponentsBuilder
                .fromUriString(departmentBaseUrl)
                .path("/api/department/"+id)
                .toUriString();
    }

    public String deleteDepartmentById(Long id) {
        return apiClientHelper.delete(buildUriFromForDeleteDepartmentById(id))
                .doOnError(
                        er-> System.out.println(er.getMessage())
                )
                .onErrorResume(
                        er->{
                            return Mono.just("failed to delete the department for the id: "+id);
                        }
                )
                .block();
    }

    private String buildUriFromForDeleteDepartmentById(Long id){
        return UriComponentsBuilder
                .fromUriString(departmentBaseUrl)
                .path("/api/department/"+id)
                .toUriString();
    }*/

    /*public DepartmentDTO createDepartment(DepartmentDTO departmentDTO){
        return restTemplteHelper.post(buildUriFormForPostDepartment(),departmentDTO, DepartmentDTO.class);
    }

    public List<DepartmentDTO> findAllDepartments(){
        return restTemplteHelper.getList(buildUriFromForGetAllDepartment(),
                new ParameterizedTypeReference<List<DepartmentDTO>>() {
                });
    }*/

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO){
        return departmentFeignClient.createDepartment(departmentDTO);
    }

    /*@CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "departmentFallback"
    )
    @Retry(
            name = "departmentRetry",
            fallbackMethod = "departmentFallback"
    )*/
    @RateLimiter(
            name = "departmentRateLimiter",
            fallbackMethod = "departmentRateLimiterFallback"
    )
    public  List<DepartmentDTO> findAllDepartments(){
        System.out.println("Calling Department Service...");
        return departmentFeignClient.findAllDepartments();
    }
    public DepartmentDTO findDepartmentById(Long id){
        return departmentFeignClient.findDepartmentById(id);
    }

    public List<DepartmentDTO> departmentFallback(Exception ex){
        System.out.println("Fallback executed: "+ex.getClass().getSimpleName());

        DepartmentDTO departmentDTO=new DepartmentDTO();
        departmentDTO.setId(0L);
        departmentDTO.setDepartmentName("Department Service Unavailable");

        return List.of(departmentDTO);
    }

    public List<DepartmentDTO> departmentRateLimiterFallback(
            Exception ex) {

        System.out.println("Rate limit exceeded");

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(-1L);
        dto.setDepartmentName("Too Many Requests");

        return List.of(dto);
    }
}
