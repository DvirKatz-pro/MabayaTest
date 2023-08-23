package com.example.Mabaya.Repositories;

import com.example.Mabaya.Repositories.Entity.Campaign;
import com.example.Mabaya.Repositories.Entity.Product;
import org.springframework.stereotype.Repository;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class ProductRepository implements IProductRepository {

    public enum Categories {
        ELECTRONICS, OUTDOORS, HYGIENE, HOME, HEALTH, SCHOOL, UNKNOWN
    }

    private Map<Long, Product> products = new ConcurrentHashMap<>();

    @Override
    public Product createNewProduct(Product product) throws Exception {
        if (products.containsKey(product.getSerialNumber())) {
            throw new Exception("A product with this serial number already exists");
        }
        products.put(product.getSerialNumber(), product);
        return readProductBySerialNumber(product.getSerialNumber());
    }

    @Override
    public Product readProductBySerialNumber(Long serial) {
        return products.get(serial);
    }

    @Override
    public Product updateProduct(Product product) throws Exception {
        if (!products.containsKey(product.getSerialNumber())) {
            throw new Exception("No product with this serial number could be found to update");
        }
        products.replace(product.getSerialNumber(), product);
        return readProductBySerialNumber(product.getSerialNumber());
    }

    @Override
    public void deleteProductBySerialNumber(Long serial)  {
        products.remove(serial);
    }

    @Override
    public List<Product> getAll() {
        return List.copyOf(products.values());
    }
}
