package com.example.Mabaya.Repositories;

import com.example.Mabaya.Repositories.Entity.Campaign;
import com.example.Mabaya.Repositories.Entity.Product;

import java.util.List;
import java.util.Map;

public interface ICampaignRepository {
    public Campaign createNewCampaign(Campaign campaign) throws Exception;

    public Campaign readCampaignByName(String name);

    public Campaign updateCampaign(Campaign campaign) throws Exception;

    public void deleteCampaignByName(String name) throws Exception;

    public void deleteProductFromCampaigns(Long serialNumber, ProductRepository.Categories category);

    public Product getPromotedProduct(ProductRepository.Categories category);

    public Map<String, Campaign> getAll();

    public List<Campaign> getAllAsList();
}