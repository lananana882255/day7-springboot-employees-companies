package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import com.example.SpringBootDemo.Service.CompanyService;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CompanyRespository {
    private List<Company> companyList= new ArrayList<>();
    private static long nextId=1;

    public void clearComanies() {
        this.companyList.clear();
        nextId=1;
    }

    public void save(Company company) {
        company.setId(nextId++);
        companyList.add(company);
    }

    public List<Company> getCompanies(Integer page,Integer size) {
        List<Company> filteredCompanyList=companyList;
        if(page!=null&&size!=null)
        {
            int start = (page - 1) * size;
            if (start >=filteredCompanyList.size()) {
                return Collections.emptyList();
            }
            int end = Math.min(start + size, filteredCompanyList.size());
            filteredCompanyList=filteredCompanyList.subList(start,end);
        }
        return filteredCompanyList;
    }

    public Company getCompanyById(long id) {
        return companyList.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst().orElse(null);
    }

    public boolean delete(long id) {
        return companyList.removeIf(company -> company.getId()==id);
    }

    public Company updateCompany(long id, Map<String, Object> updateName) {
        Company updateCompany=companyList.stream().filter(company -> company.getId()==id).findFirst().orElse(null);
        if(updateCompany==null){
            return null;
        }
        if(updateName.containsKey("name")){
            updateCompany.setName(updateName.get("name").toString());
        }
        return updateCompany;
    }
}
