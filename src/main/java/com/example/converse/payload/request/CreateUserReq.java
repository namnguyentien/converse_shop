package com.example.converse.payload.request;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserReq implements Serializable {
    @NotBlank(message = "userName must be not blank")
    private String username;

    @NotBlank(message = "email must be not blank")
    @Email(message = "email invalid format")
    private String email;

    @NotBlank(message = "password must be not blank")
    private String password;

    private Set<String> role;

}
