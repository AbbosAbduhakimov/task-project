package com.example.controller;

import com.example.model.Company;
import com.example.response.CustomResponseHandler;
import com.example.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCompany(@PathVariable(value = "id") Long companyId){
        try {
            Company response = companyService.getCompany(companyId);
            return CustomResponseHandler.generateResponse("Successfully retrieved data!",HttpStatus.OK,response);
        }
        catch (Exception e){
            return CustomResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/block/{id}")
    public ResponseEntity<?> blockingCompany(@PathVariable(value = "id") Long companyId){
        try {
            companyService.blockCompany(companyId);
            return CustomResponseHandler.generateResponse("Company successfully blocked", HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createCompany(@RequestBody Company company){
        try {
            companyService.createCompany(company);
            return CustomResponseHandler.generateResponse("Company successfully created",HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
