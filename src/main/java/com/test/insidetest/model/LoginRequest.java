package com.test.insidetest.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    private String name;

    @NotNull
    private String password;
}
