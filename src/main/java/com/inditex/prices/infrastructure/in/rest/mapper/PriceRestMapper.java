package com.inditex.prices.infrastructure.in.rest.mapper;
import com.inditex.prices.application.dto.ApplicablePriceDto;
import com.inditex.prices.generated.model.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRestMapper {

    PriceResponse toResponse(ApplicablePriceDto dto);
}