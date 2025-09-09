package com.example.SpringBootDemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CompanyController {
    private List<Company> companyList= new ArrayList<>();

    @PostMapping("/companies")
    public ResponseEntity<Map<String, Object>> createCompany(@RequestBody Company company){
        companyList.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",company.getId()));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id){
        return companyList.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/companies")
    public List<Company> getCompanies(){

        return new ArrayList<>(companyList);

    }
}
