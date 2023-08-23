package com.example.Mabaya.Controllers;

import com.example.Mabaya.Controllers.DTO.CampaignDTO;
import com.example.Mabaya.Repositories.CampaignRepository;
import com.example.Mabaya.Repositories.Entity.Campaign;
import com.example.Mabaya.Repositories.Entity.Product;
import com.example.Mabaya.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductRepository productRepository;
    CampaignRepository campaignRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, CampaignRepository campaignRepository) {
        this.productRepository = productRepository;
        this.campaignRepository = campaignRepository;
    }

    /**
     * create a new Product
     *
     * @param product to create
     * @return the created product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createNewProduct(@RequestBody Product product) {
        Product createdProduct = null;
        try {
            createdProduct = productRepository.createNewProduct(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(createdProduct);
    }

    /**
     * Given a serial number return an associated product
     *
     * @param serial number of the product
     * @return the product if it exists
     */
    @GetMapping("/{serial}")
    public ResponseEntity<Product> readProductBySerialNumber(@PathVariable Long serial) {
        Product product = productRepository.readProductBySerialNumber(serial);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * update an existing Product
     *
     * @param product instance to update
     * @return the updated product if it exists
     */
    @PutMapping
    public ResponseEntity<Object> updateCampaign(@RequestBody Product product) {
        Product updatedProduct = null;
        try {
            updatedProduct = productRepository.updateProduct(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * delete an existing product
     *
     * @param serial number of the product to delete
     * @return all the remaining Products after deletion
     */
    @DeleteMapping("/{serial}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long serial) {
        Product product = productRepository.readProductBySerialNumber(serial);
        if (product == null) {
            return ResponseEntity.badRequest().body("Could not find a product with this serial number");
        }
        productRepository.deleteProductBySerialNumber(product.getSerialNumber());
        campaignRepository.deleteProductFromCampaigns(product.getSerialNumber(), product.getCategory());
        return ResponseEntity.ok(productRepository.getAll());
    }
    /**
     * @return every created product currently saved
     */
    @GetMapping
    public List<Product> getAll() {
        return productRepository.getAll();
    }
}
