package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRespository {
    void clearComanies();

    Company save(Company company);

    List<Company> getCompanies(Integer page, Integer size);

    Company getCompanyById(long id);

    boolean delete(long id);

    Company updateCompany(long id, Company  updateName);
}
