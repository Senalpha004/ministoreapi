package com.ministore.ministoreapi.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity //makes the class a database table
public class Product {

    //this class holds the details required of the product

    @Id //marks the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;

    public Product() {

    }

    //constructor to be used by the creating objects and assigning the input values
    public Product(long id, String name, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;

    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
