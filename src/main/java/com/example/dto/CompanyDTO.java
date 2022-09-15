package com.example.dto;

import com.example.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {

    private Long id;

    @NotBlank(message = "company name can't be null or empty")
    private String name;

    @NotBlank(message = "address can't be null or empty")
    private String address;

    @NotBlank(message = "zipcode can't be null or empty")
    private String zipCode;

    private Boolean isBlocked;

    private List<User> users = new LinkedList<>();
}
