package com.example.Mabaya.Repositories;

import com.example.Mabaya.Repositories.Entity.Campaign;
import com.example.Mabaya.Repositories.Entity.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CampaignRepository implements ICampaignRepository {
    private Map<String, Campaign> campaigns = new ConcurrentHashMap<>();
    private ProductRepository products;

    @Override
    public Campaign createNewCampaign(Campaign campaign) throws Exception {
        if (campaigns.containsKey(campaign.getName())) {
            throw new Exception("A Campaign with this name already exists");
        }
        campaigns.put(campaign.getName(), campaign);
        return readCampaignByName(campaign.getName());
    }

    @Override
    public Campaign readCampaignByName(String name) {
        return campaigns.get(name);
    }

    @Override
    public Campaign updateCampaign(Campaign campaign) throws Exception {
        if (!campaigns.containsKey(campaign.getName())) {
            throw new Exception("No Campaign with this name could be found to update");
        }
        campaigns.replace(campaign.getName(), campaign);
        return readCampaignByName(campaign.getName());
    }

    @Override
    public void deleteCampaignByName(String name) throws Exception {
        if (!campaigns.containsKey(name)) {
            throw new Exception("No Campaign with this name could be found to delete");
        }
        campaigns.remove(name);
    }

    @Override
    public void deleteProductFromCampaigns(Long serialNumber, ProductRepository.Categories category) {
        for (Map.Entry<String, Campaign> entry : campaigns.entrySet()) {
            Campaign campaign = entry.getValue();
            if (campaign.getProducts().containsKey(category)) {
                List<Product> products = campaign.getProducts().get(category);
                for (int i = products.size() - 1; i >= 0; i--) {
                    if (products.get(i).getSerialNumber().equals(serialNumber)) {
                        products.remove(products.get(i));
                    }
                }
            }
        }
    }

    @Override
    public Product getPromotedProduct(ProductRepository.Categories category) {

        //get the campaign with the highest bid
        List<Campaign> sortedBidList = new ArrayList<>(List.copyOf(campaigns.values()));
        sortedBidList.sort(new CampaignBidComparator());

        boolean isFoundHighestBidCampaign = false;
        Campaign highestBidCampaign = null;
        LocalDate currentDate = LocalDate.now();
        LocalDate checkDate = currentDate.minusDays(10);

        for (Campaign c : sortedBidList) {
            LocalDate campaignDate = c.getStartDate();
            boolean validDate = campaignDate.isAfter(checkDate) && campaignDate.isBefore(currentDate);
            //save the first valid campaign with the highest bid
            if (!isFoundHighestBidCampaign && validDate) {
                isFoundHighestBidCampaign = true;
                highestBidCampaign = c;
                //check if this campaign has a product from needed category
                if (category == null) {
                    Map.Entry<ProductRepository.Categories, List<Product>> entry = highestBidCampaign.getProducts().entrySet().iterator().next();
                    return entry.getValue().get(0);
                }
            }
            //check campaigns with lower bids if they are valid and have the needed category
            if (c.getProducts().containsKey(category) && validDate) {
                return c.getProducts().get(category).get(0);
            }
        }
        //if we havent found a product from the needed category, then return the first product from the valid campaign with the highest bid
        if (highestBidCampaign != null) {
            Map.Entry<ProductRepository.Categories, List<Product>> entry = highestBidCampaign.getProducts().entrySet().iterator().next();
            return entry.getValue().get(0);
        }

        return null;
    }

    @Override
    public Map<String, Campaign> getAll() {
        return campaigns;
    }

    @Override
    public List<Campaign> getAllAsList() {
        return List.copyOf(campaigns.values());
    }

    /**
     * sort the Campaigns from highest to smallest bid
     */
    static class CampaignBidComparator implements Comparator<Campaign> {
        public int compare(Campaign a, Campaign b) {
            return b.getBid().compareTo(a.getBid());
        }
    }
}