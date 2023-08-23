package com.example.Mabaya.Repositories.Entity;

import com.example.Mabaya.Repositories.ProductRepository;
import lombok.*;
import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    String title;
    @Enumerated(EnumType.STRING)
    ProductRepository.Categories category;
    Double price;
    @Id
    Long serialNumber;


}
