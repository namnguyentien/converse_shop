package com.example.converse.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductReq {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private long price;

    @NotNull
    private int quantity;

    @NotNull
    private long category_id;

    @JsonProperty("image_ids")
    private ArrayList<Long> imageIds;
}
