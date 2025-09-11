package com.example.SpringBootDemo.Service;

import com.example.SpringBootDemo.Company;
import com.example.SpringBootDemo.Repository.CompanyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    @Autowired
    private CompanyRespository companyRespository;

    public void clearComanies() {
        this.companyRespository.clearComanies();
    }

    public void createCompany(Company company) {
        companyRespository.save(company);
    }

    public List<Company> getCompanies(Integer page, Integer size) {

        List<Company> filteredCompanyList = companyRespository.getCompanies(page, size);

        return filteredCompanyList;
    }

    public Company getCompanyById(long id) {
        return companyRespository.getCompanyById(id);
    }

    public Company updateEmployee(long id, String updateName) {

        Company updateCompany = companyRespository.updateCompany(id, updateName);
        return updateCompany;
    }

    public boolean delete(long id) {
        return companyRespository.delete(id);
    }
}
