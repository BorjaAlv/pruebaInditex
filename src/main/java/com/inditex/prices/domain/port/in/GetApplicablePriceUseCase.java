package com.inditex.prices.domain.port.in;

import com.inditex.prices.application.dto.ApplicablePriceDto;

import java.time.LocalDateTime;

public interface GetApplicablePriceUseCase {

    ApplicablePriceDto getApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId
    );

}
