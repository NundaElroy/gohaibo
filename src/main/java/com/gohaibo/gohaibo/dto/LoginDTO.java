package com.gohaibo.gohaibo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message= "Email is mandatory and cannot be blank")
    private String email;
    @NotBlank(message= "Password is mandatory and cannot be blank")
    private String password;

}
