package com.ministore.ministoreapi.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    @NotBlank(message = "Category name is required!")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String catName) {
        this.name = catName;
    }

}
