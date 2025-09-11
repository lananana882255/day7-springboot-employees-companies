package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class CompanyRespositoryInMenmoryImp implements CompanyRespository {
    private final List<Company> companyList = new ArrayList<>();
    private static long nextId = 1;

    @Override
    public void clearComanies() {
        this.companyList.clear();
        nextId = 1;
    }

    @Override
    public void save(Company company) {
        company.setId(nextId++);
        companyList.add(company);
    }

    @Override
    public List<Company> getCompanies(Integer page, Integer size) {
        List<Company> filteredCompanyList = companyList;
        if (page != null && size != null) {
            int start = (page - 1) * size;
            if (start >= filteredCompanyList.size()) {
                return Collections.emptyList();
            }
            int end = Math.min(start + size, filteredCompanyList.size());
            filteredCompanyList = filteredCompanyList.subList(start, end);
        }
        return filteredCompanyList;
    }

    @Override
    public Company getCompanyById(long id) {
        return companyList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public boolean delete(long id) {
        return companyList.removeIf(company -> company.getId() == id);
    }

    @Override
    public Company updateCompany(long id, String updateName) {
        Company updateCompany = companyList.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
        if (updateCompany == null) {
            return null;
        }
        if (updateName!=null) {
            updateCompany.setName(updateName);
        }
        return updateCompany;
    }
}
