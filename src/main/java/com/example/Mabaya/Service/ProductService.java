package com.example.Mabaya.Service;

import com.example.Mabaya.Repositories.Entity.Product;
import com.example.Mabaya.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductService implements CommandLineRunner {

    ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        initializeProducts();
    }

    /**
     * create our initial products for testing
     */
    public void initializeProducts() {
        Product product = new Product("Computer", ProductRepository.Categories.ELECTRONICS, 1000.56, 1L);
        Product product1 = new Product("Tent", ProductRepository.Categories.OUTDOORS, 400D, 2L);
        Product product2 = new Product("Desk", ProductRepository.Categories.HOME, 600.34, 3L);
        Product product3 = new Product("Weights", ProductRepository.Categories.HEALTH, 100.99, 4L);
        Product product4 = new Product("Toothbrush", ProductRepository.Categories.HYGIENE, 10D, 5L);
        Product product5 = new Product("Backpack", ProductRepository.Categories.SCHOOL, 200.45, 6L);
        Product product6 = new Product("Laptop", ProductRepository.Categories.ELECTRONICS, 1500.44, 7L);
        Product product7 = new Product("Yoga ball", ProductRepository.Categories.HEALTH, 500.5, 8L);
        try {
            productRepository.createNewProduct(product);
            productRepository.createNewProduct(product1);
            productRepository.createNewProduct(product2);
            productRepository.createNewProduct(product3);
            productRepository.createNewProduct(product4);
            productRepository.createNewProduct(product5);
            productRepository.createNewProduct(product6);
            productRepository.createNewProduct(product7);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}