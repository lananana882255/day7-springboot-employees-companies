package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJPARepository extends JpaRepository<Company, Long> {
}
