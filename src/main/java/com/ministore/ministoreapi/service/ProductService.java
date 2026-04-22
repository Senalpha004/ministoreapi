package com.ministore.ministoreapi.service;

import com.ministore.ministoreapi.dto.ProductResponse;
import com.ministore.ministoreapi.model.Category;
import com.ministore.ministoreapi.model.Product;
import com.ministore.ministoreapi.dto.ProductRequest;
import com.ministore.ministoreapi.exception.NotFoundException;
import com.ministore.ministoreapi.repository.CategoryRepository;
import com.ministore.ministoreapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    //this class is called by the controller and handles the main logic of the program
    //this the logic operator which stores and handles product IDs, rules and errors.
    //aka brain.
/*
    //data storage in memory later to be replaced with DB
    private final Map<Long, Product> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0); //generates the ID automatically


    //method to create a product request easily for seed()
    private ProductRequest req(String name, BigDecimal price, int stock){
        ProductRequest pr =  new ProductRequest();
        pr.setName(name);
        pr.setPrice(price);
        pr.setStock(stock);
        return pr;
    }

    //method to return all the products like view all option
    public List<Product> search(String q, BigDecimal minPrice, BigDecimal maxPrice){
        return store.values().stream()
                .filter(p -> q == null || p.getName().toLowerCase().contains(q.toLowerCase()))
                .filter(p -> minPrice == null || p.getPrice().compareTo(minPrice) >= 0)
                .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
                .toList()
                ;
    }

    //method to return by ID like search option
    public Product getById(Long id){
        Product p = store.get(id);
        if (p == null) throw new NotFoundException("Product not found");
        return p;
    }

    //creates a product with a new ID like add product option
    public Product create(ProductRequest req){
        long id = idGen.incrementAndGet(); //increases the id number to get a new id
        Product p = new Product(id, req.getName(), req.getPrice(), req.getStock());
        store.put(id, p);
        return p;
    }

    //updates existing product
    public Product update(Long id, ProductRequest req){
        Product existing = getById(id); //ensures it exists
        existing.setName(req.getName());
        existing.setPrice(req.getPrice());
        existing.setStock(req.getStock());
        return existing;
    }

    //updating only stock
    public Product updateStock(Long id, Integer delta){
        Product existing = getById(id);

        int newStock = existing.getStock() + delta;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock is out of range!");
        }
        existing.setStock(newStock);
        return existing;
    }

    //deletes the product or throws error if it doesn't exist
    public void delete(Long id){
        if (store.remove(id) == null) throw new NotFoundException("Product not found");
    }

    //constructor running once when the app starts
    public ProductService(){
        create(req("Milk", BigDecimal.valueOf(450), 10));
        create(req("Bread", BigDecimal.valueOf(250), 25));
    }*/

    //after creating a product repo

    private final ProductRepository repository;
    private final CategoryRepository cr;

    public ProductService(ProductRepository repository, CategoryRepository catRepo) {

        this.repository = repository;
        this.cr = catRepo;
    }


    public List<ProductResponse> getAll() {

        List<Product> productList = repository.findAll();
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : productList) {

            ProductResponse productResponse = new ProductResponse();

            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setStock(product.getStock());

            if (product.getCategory() != null){
                productResponse.setCategoryId(product.getCategory().getId());
                productResponse.setCategory(product.getCategory().getName());
            }

            productResponseList.add(productResponse);
        }

        return productResponseList;
    }




    public ProductResponse getById(Long id) {

        Product foundProduct = repository.findById(id).orElseThrow (

                () -> new NotFoundException("Product not found with id: " + id)
        );

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(foundProduct.getId());
        productResponse.setName(foundProduct.getName());
        productResponse.setPrice(foundProduct.getPrice());
        productResponse.setStock(foundProduct.getStock());

        if (foundProduct.getCategory() != null){
            productResponse.setCategoryId(foundProduct.getCategory().getId());
            productResponse.setCategory(foundProduct.getCategory().getName());
        }

        return productResponse;
    }




    public ProductResponse create(ProductRequest req){

        //if the same product name exists throw error
        if (repository.existsByNameIgnoreCase(req.getName().trim())){
            throw new IllegalArgumentException("Product with name " + req.getName() + " already exists!");
        }

        //when the category doesn't exist to create a product under the category
        Category category = cr.findById(req.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));


        Product p = new Product();

        p.setName(req.getName().trim());
        p.setPrice(req.getPrice());
        p.setStock(req.getStock());
        p.setCategory(category);

        Product savedProduct = repository.save(p);

        ProductResponse productResponse =  new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setStock(savedProduct.getStock());

        if (savedProduct.getCategory() != null){
            productResponse.setCategoryId(savedProduct.getCategory().getId());
            productResponse.setCategory(savedProduct.getCategory().getName());
        }

        return productResponse;
    }




    public ProductResponse update(Long id, ProductRequest req) {

        Product existing = repository.findById(id).orElseThrow(() -> new NotFoundException("Product does not exist"));

        Category category = cr.findById(req.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));


       // Product existing = new Product(); //ensures it exists
        existing.setName(req.getName());
        existing.setPrice(req.getPrice());
        existing.setStock(req.getStock());
        existing.setCategory(category);

        Product updatedProduct = repository.save(existing);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(updatedProduct.getId());
        productResponse.setName(updatedProduct.getName());
        productResponse.setPrice(updatedProduct.getPrice());
        productResponse.setStock(updatedProduct.getStock());

        if (updatedProduct.getCategory() != null){
            productResponse.setCategoryId(updatedProduct.getCategory().getId());
            productResponse.setCategory(updatedProduct.getCategory().getName());
        }

        return productResponse;

    }




    public void delete(Long id) {

        Product existing = repository.findById(id).orElseThrow (

                () -> new NotFoundException("Product not found with id: " + id));

        repository.delete(existing);
    }




    public ProductResponse updateStock(Long id, Integer delta) {
        //delta is the amount of change like +5 or -2 from the existing stock amount

        Product existing = repository.findById(id).orElseThrow (

                () -> new NotFoundException("Product not found with id: " + id));


        //adding/reducing the change amount to the existing amount and assigning it to a new variable
        int newStock = existing.getStock() + delta;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock is out of range!");
        }

        existing.setStock(newStock);
        //saving the new updated stock to Product class
        Product updatedStock =  repository.save(existing);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(updatedStock.getId());
        productResponse.setName(updatedStock.getName());
        productResponse.setPrice(updatedStock.getPrice());
        productResponse.setStock(updatedStock.getStock());

        if (updatedStock.getCategory() != null){
            productResponse.setCategoryId(updatedStock.getCategory().getId());
            productResponse.setCategory(updatedStock.getCategory().getName());
        }

        return productResponse;
    }




    public List<Product> search(String q, BigDecimal minPrice, BigDecimal maxPrice){
        return repository.findAll().stream()
                .filter(p -> q == null || p.getName().toLowerCase().contains(q.toLowerCase()))
                .filter(p -> minPrice == null || p.getPrice().compareTo(minPrice) >= 0)
                .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
                .toList();
    }

}
