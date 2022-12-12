package com.mentor4you.controller;

import com.mentor4you.domain.dto.AddPricelistDTO;
import com.mentor4you.domain.dto.PricelistDTO;
import com.mentor4you.domain.mapper.PricelistMapper;
import com.mentor4you.service.PricelistService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricelists")
@RequiredArgsConstructor
public class PricelistController {

  private final PricelistService pricelistService;
  private final PricelistMapper pricelistMapper;

  @GetMapping
  public List<PricelistDTO> getAllPricelists() {
    return pricelistService.getAllPricelists().stream()
        .map(pricelistMapper::toPricelistDTO)
        .toList();
  }

  @GetMapping("/{pricelistId}")
  public PricelistDTO getPricelistById(@PathVariable Integer pricelistId) {
    return pricelistMapper.toPricelistDTO(pricelistService.getPricelistById(pricelistId));
  }

  @PostMapping
  public ResponseEntity<?> addPricelist(@RequestBody AddPricelistDTO addPricelistDTO) {
    pricelistService.addPricelist(addPricelistDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
