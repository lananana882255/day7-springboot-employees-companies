package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CompanyRespository {
    void clearComanies();

    void save(Company company);

    List<Company> getCompanies(Integer page, Integer size);

    Company getCompanyById(long id);

    boolean delete(long id);

    Company updateCompany(long id, String  updateName);
}
