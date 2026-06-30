package com.example.Jwt_Token.controller;

import com.example.Jwt_Token.entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/practice")
public class JwtController {

     List<Employee> employeeList=new ArrayList<>(
             List.of(
                new Employee(1L,"Gangadhara",15000),
                new Employee(2L,"Ranganath",30000)
             )
     );

     @GetMapping
     public List<Employee> findAllEmployees(){
         return employeeList;
     }

     @PostMapping
    public boolean createEmployee(@RequestBody Employee employee){
         return employeeList.add(employee);
     }
}
