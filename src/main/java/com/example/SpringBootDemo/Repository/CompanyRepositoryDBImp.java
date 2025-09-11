package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepositoryDBImp implements CompanyRespository {
    @Autowired
    private CompanyJPARepository companyJPARepository;

    @Override
    public void clearComanies() {
        companyJPARepository.deleteAll();
    }

    @Override
    public void save(Company company) {
        companyJPARepository.save(company);
    }

    @Override
    public List<Company> getCompanies(Integer page, Integer size) {
        return companyJPARepository.findAll();
    }

    @Override
    public Company getCompanyById(long id) {
        return companyJPARepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
    }

    @Override
    public boolean delete(long id) {
        companyJPARepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
        companyJPARepository.deleteById(id);
        return true;

    }

    @Override
    public Company updateCompany(long id, String companyName) {
        Company updateCompany=companyJPARepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
        updateCompany.setName(companyName);
        companyJPARepository.save(updateCompany);
        return null;
    }
}
