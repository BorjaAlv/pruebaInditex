package com.inditex.prices.infrastructure.in.rest.controller;

import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.infrastructure.in.rest.mapper.PriceRestMapper;
import com.inditex.prices.generated.api.PricesApi;
import com.inditex.prices.generated.model.PriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;
    private final PriceRestMapper priceRestMapper;

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    ) {
        var applicablePrice = getApplicablePriceUseCase.getApplicablePrice(
                applicationDate,
                productId,
                brandId
        );

        return ResponseEntity.ok(priceRestMapper.toResponse(applicablePrice));
    }
}