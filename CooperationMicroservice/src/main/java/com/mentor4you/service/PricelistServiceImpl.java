package com.mentor4you.service;

import com.mentor4you.domain.dto.AddPricelistDTO;
import com.mentor4you.domain.mapper.PricelistMapper;
import com.mentor4you.domain.model.Category;
import com.mentor4you.domain.model.Pricelist;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.repository.PricelistRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricelistServiceImpl implements PricelistService {

  private final PricelistRepository pricelistRepository;
  private final CategoryService categoryService;
  private final PricelistMapper pricelistMapper;

  @Override
  public List<Pricelist> getAllPricelists() {
    final List<Pricelist> pricelists = new ArrayList<>();
    pricelistRepository.findAll().forEach(pricelists::add);
    return pricelists;
  }

  @Override
  public Pricelist getPricelistById(Integer pricelistId) {
    return pricelistRepository.findById(pricelistId).orElseThrow(() -> new EntityNotFoundException("Pricelist with provided id doesn't exist"));
  }

  @Override
  public Pricelist addPricelist(AddPricelistDTO addPricelistDTO) {
    final Category category = categoryService.getCategoryById(addPricelistDTO.getCategoryId());
    final Pricelist pricelistToAdd = pricelistMapper.toPricelist(addPricelistDTO);
    pricelistToAdd.setCategory(category);
    pricelistRepository.save(pricelistToAdd);
    return pricelistToAdd;
  }
}
