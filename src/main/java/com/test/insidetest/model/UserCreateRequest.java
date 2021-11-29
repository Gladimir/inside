package com.test.insidetest.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreateRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 4, max = 16, message = "Name should not be less than 4 characters, and bigger than 16 characters")
    private String name;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 16, message = "Password should not be less than 6 characters, and bigger than 16 characters")
    private String password;
}
