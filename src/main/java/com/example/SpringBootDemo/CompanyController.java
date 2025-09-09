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
    public List<Company> getCompanies(@RequestParam(required = false, defaultValue = "1") int page
            , @RequestParam(required = false, defaultValue = "5") int size){
        List<Company> getCompaniesList= companyList;
        int start = (page - 1) * size;
        if (start >= getCompaniesList.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(start + size, getCompaniesList.size());
        return getCompaniesList.subList(start,end);
    }

    @PutMapping("/companies/{id}/name")
    public ResponseEntity<Company> updateCompanyName(@PathVariable  long id,@RequestBody Map<String,Object> updateName){
        Company updateCompany=companyList.stream().filter(company -> company.getId()==id).findFirst().orElse(null);
        if(updateCompany==null){
            return ResponseEntity.notFound().build();
        }
        if(updateName.containsKey("name")){
            updateCompany.setName(updateName.get("name").toString());
        }
        return ResponseEntity.ok(updateCompany);
    }
}
