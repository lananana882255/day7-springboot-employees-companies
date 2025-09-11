package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CompanyRepositoryDBImp implements CompanyRespository {
    @Autowired
    private CompanyJPARepository companyJPARepository;

    @Override
    public void clearComanies() {

    }

    @Override
    public void save(Company company) {

    }

    @Override
    public List<Company> getCompanies(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Company getCompanyById(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Company updateCompany(long id, Map<String, Object> updateName) {
        return null;
    }
}
