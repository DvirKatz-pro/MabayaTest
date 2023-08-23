package com.example.Mabaya.Controllers.DTO;

import com.example.Mabaya.Repositories.Entity.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * DTO to convert to Campaign Entity
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CampaignDTO {
    String name;
    String startDate;
    Double bid;
    List<Long> productSerialNumbers;
}
