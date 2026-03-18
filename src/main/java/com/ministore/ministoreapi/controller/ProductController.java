package com.ministore.ministoreapi.controller;

import com.ministore.ministoreapi.dto.ProductRequest;
import com.ministore.ministoreapi.model.Product;
import com.ministore.ministoreapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //returns JSON automatically
@RequestMapping("/api/products") //base URL
public class ProductController {
    private final ProductService service;

    //dependency injection
    public ProductController(ProductService service) {
        this.service = service;
    }

    //GET /api/products
    @GetMapping
    public List<Product> getAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) java.math.BigDecimal minPrice,
            @RequestParam(required = false) java.math.BigDecimal maxPrice
    ){
        return service.search(q, minPrice, maxPrice);
    }

    //GET /api/products/1
    //to get a specific product
    @GetMapping("/{id}")
    public Product getOne(@PathVariable Long id){
        return service.getById(id);
    }

    //POST /api/products
    //adding a product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductRequest req){
        return service.create(req);
    }

    //PUT /api/products/1
    //updating a product detail
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest req){
        return service.update(id, req);
    }

    //updating only selected parts like stock or changing the name
    @PatchMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @RequestParam Integer delta){
        return service.updateStock(id, delta);
    }

    //DELETE /api/products/1
    //deleting a product by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
