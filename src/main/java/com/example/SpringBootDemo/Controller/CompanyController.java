package com.example.SpringBootDemo.Controller;

import com.example.SpringBootDemo.Company;
import com.example.SpringBootDemo.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/companies")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    public void clearCompanies() {
        this.companyService.clearComanies();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCompany(@RequestBody Company company) {
        Company savedCompany= companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", savedCompany.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }


    @GetMapping
    public List<Company> getCompanies(@RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            return companyService.getCompanies(page, size);
        }
        return companyService.getCompanies(page, size);
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<Company> updateCompanyName(@PathVariable long id, @RequestBody Company updateName) {
        Company updateCompany = companyService.updateEmployee(id, updateName);
        if (updateCompany == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
        if (companyService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
