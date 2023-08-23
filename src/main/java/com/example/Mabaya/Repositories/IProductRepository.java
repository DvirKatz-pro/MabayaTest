package com.example.Mabaya.Repositories;


import com.example.Mabaya.Repositories.Entity.Campaign;
import com.example.Mabaya.Repositories.Entity.Product;

import java.util.List;

public interface IProductRepository {
    public Product createNewProduct(Product product) throws Exception;

    public Product readProductBySerialNumber(Long serial);

    public Product updateProduct(Product product) throws Exception;

    public void deleteProductBySerialNumber(Long serial) throws Exception;

    public List<Product> getAll();
}