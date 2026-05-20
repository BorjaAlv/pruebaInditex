package com.inditex.prices.application.service;

import com.inditex.prices.application.dto.ApplicablePriceDto;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final PriceRepository priceRepositoryPort;

    @Override
    public ApplicablePriceDto getApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    ) {
        return priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId)
                .map(price -> new ApplicablePriceDto(
                        price.productId(),
                        price.brandId(),
                        price.priceList(),
                        price.startDate(),
                        price.endDate(),
                        price.price(),
                        price.currency()
                ))
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, applicationDate));
    }
}