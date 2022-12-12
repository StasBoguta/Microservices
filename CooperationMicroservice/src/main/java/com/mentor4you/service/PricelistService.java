package com.mentor4you.service;

import com.mentor4you.domain.dto.AddPricelistDTO;
import com.mentor4you.domain.model.Pricelist;
import java.util.List;

public interface PricelistService {

  List<Pricelist> getAllPricelists();

  Pricelist getPricelistById(Integer pricelistId);

  Pricelist addPricelist(AddPricelistDTO addPricelistDTO);
}
