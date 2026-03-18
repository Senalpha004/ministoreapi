package com.ministore.ministoreapi.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequest {
    //this class holds te validations rules for the user like what the user can/not send

    //name cannot be empty
    @NotBlank(message = "Name is Required ^ ")
    private String name;

    //price should be greater than zero
    @NotNull(message = "Price cannot be Empty!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price cannot be Negative")
    private BigDecimal price;

    //stock must be greater than zero
    @NotNull(message = "Stock cannot be Empty!")
    @Min(value = 0, message = "Stock cannot be Negative!")
    private Integer stock;

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
