package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Company save(Company company) {
        return companyJPARepository.save(company);
    }

    @Override
    public List<Company> getCompanies(Integer page, Integer size) {

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page - 1, size);
            return companyJPARepository.findAll(pageable).getContent();
        }
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
    public Company updateCompany(long id, Company companyName) {
        Company updateCompany = getCompanyById(id);
        updateCompany.setName(companyName.getName());
        return companyJPARepository.save(updateCompany);
    }
}
