package com.example.Mabaya.Repositories.Entity;

import com.example.Mabaya.Repositories.ProductRepository;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Campaign {
    String name;
    LocalDate startDate;
    Double bid;
    HashMap<ProductRepository.Categories,List<Product>> products;

}
