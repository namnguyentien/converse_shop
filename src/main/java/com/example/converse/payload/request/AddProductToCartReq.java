package com.example.converse.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartReq {

    @NotNull
    private long productId;

    @NotNull
    private int quantity;
}
