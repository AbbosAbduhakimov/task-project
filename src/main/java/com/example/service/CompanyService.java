package com.example.service;

import com.example.exception.ProjectNotFoundException;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company getCompany(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new ProjectNotFoundException("Company not found"));
    }

    public Long createCompany(Company company) {
        companyRepository.save(company);
        log.info("create company {}", company);
        return company.getId();
    }

    public void blockCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ProjectNotFoundException("Company not found"));

        company.setBlock(true);
        log.info("company update {}", company);
        companyRepository.flush();
    }

}
