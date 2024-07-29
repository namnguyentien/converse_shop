package com.example.converse.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileReq {

    private String fullname;

    @Pattern(regexp = "(09|01[2|6|8|9])+([0-9]{8})\\b", message = "phone invalid format")
    private String phone;

    @NotNull
    @NotBlank
    private String email;

    private String address;

    private String note;
}
