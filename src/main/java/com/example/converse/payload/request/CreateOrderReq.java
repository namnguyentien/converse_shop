package com.example.converse.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReq {

    @NotBlank
    private String name;

    private String email;

    @NotBlank
    private String address;

    private String country;

    @Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    private String phone;

    private String note;

}
