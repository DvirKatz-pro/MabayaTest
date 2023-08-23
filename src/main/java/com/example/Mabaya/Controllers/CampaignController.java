package com.example.Mabaya.Controllers;

//import com.example.Mabaya.Controllers.DTO.CampaignDTO;
import com.example.Mabaya.Controllers.DTO.CampaignDTO;
import com.example.Mabaya.Repositories.CampaignRepository;
import com.example.Mabaya.Repositories.Entity.Campaign;
import com.example.Mabaya.Repositories.Entity.Product;
import com.example.Mabaya.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    CampaignRepository campaignRepository;
    ProductRepository productRepository;

    @Autowired
    public CampaignController(CampaignRepository campaignRepository, ProductRepository productRepository) {
        this.campaignRepository = campaignRepository;
        this.productRepository = productRepository;
    }

    /**
     * create a new campaign
     *
     * @param campaignDTO convert to a Campaign entity
     * @return the created Campaign
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createNewCampaign(@RequestBody CampaignDTO campaignDTO) {
        Campaign campaign = null;
        try {
            campaign = map(campaignDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        Campaign addedCampaign = null;
        try {
            addedCampaign = campaignRepository.createNewCampaign(map(campaignDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        if (addedCampaign == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(addedCampaign);
    }

    /**
     * Given a name return an associated campaign
     *
     * @param name of the campaign
     * @return the campaign if it exists
     */
    @GetMapping("/{name}")
    public ResponseEntity<Campaign> readCampaignByName(@PathVariable String name) {
        Campaign campaign = campaignRepository.readCampaignByName(name);
        if (campaign != null) {
            return ResponseEntity.ok(campaign);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * update an existing Campaign
     *
     * @param campaignDTO instance to update
     * @return the updated Campaign if it exists
     */
    @PutMapping
    public ResponseEntity<Object> updateCampaign(@RequestBody CampaignDTO campaignDTO) {
        Campaign campaign = null;
        try {
            campaign = map(campaignDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        Campaign updatedCampaign = null;
        try {
            updatedCampaign = campaignRepository.updateCampaign(campaign);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(updatedCampaign);
    }

    /**
     * delete an existing campaign
     *
     * @param name of the Campaign to delete
     * @return all the remaining Campaigns after deletion
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Object> deleteCampaign(@PathVariable String name) {
        try {
            campaignRepository.deleteCampaignByName(name);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(campaignRepository.getAllAsList());
    }

    /**
     * given a product category return a product from the active Campaign with the highest bid
     *
     * @param category of the desired product
     * @return the product of the given category or a random product from the Campaign with the highest bid
     */
    @GetMapping("/getPromotedProduct")
    public Object getPromotedProduct(@RequestParam(value = "category") String category) {

        ProductRepository.Categories categoryEnum = ProductRepository.Categories.UNKNOWN;
        try {
            categoryEnum = ProductRepository.Categories.valueOf(category);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("category does not exist in ProductRepository.Categories");
        }

        return campaignRepository.getPromotedProduct(categoryEnum);
    }

    /**
     * @return every created campaign currently saved
     */
    @GetMapping
    public List<Campaign> getAll() {
        return campaignRepository.getAllAsList();
    }

    /**
     * convert a DTO to an entity
     *
     * @param dto unmapped object
     * @return the mapped object as a proper Java object
     */
    public Campaign map(CampaignDTO dto) throws Exception {

        //get the products matching the given serial numbers, sort the products according to their category with a hashmap
        HashMap<ProductRepository.Categories, List<Product>> toAddToCampaign = new HashMap<>();
        for (Long serialNumber : dto.getProductSerialNumbers()) {
            Product product = productRepository.readProductBySerialNumber(serialNumber);
            if (product != null) {
                if (toAddToCampaign.containsKey(product.getCategory())) {
                    toAddToCampaign.get(product.getCategory()).add(product);
                } else {
                    List<Product> categoryProducts = new ArrayList<>();
                    categoryProducts.add(product);
                    toAddToCampaign.put(product.getCategory(), categoryProducts);
                }

            }
        }
        //we only accept a Campaign with a start date of this format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(dto.getStartDate(), formatter);
        } catch (Exception e) {
            throw new Exception("start date does not have a correct format. Try: yyyyMMdd");
        }
        if (toAddToCampaign.isEmpty()) {
            throw new Exception("No products with valid serial numbers could be added to the campaign");
        }
        return new Campaign(dto.getName(), localDate, dto.getBid(), toAddToCampaign);

    }
}
  