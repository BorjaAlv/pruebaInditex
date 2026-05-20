package com.inditex.prices.infrastructure.out.persistence.mapper;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.out.persistence.entity.PriceJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PricePersistenceMapper {

    Price toDomain(PriceJpaEntity entity);
}
