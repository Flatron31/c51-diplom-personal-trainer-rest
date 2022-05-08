package com.example.c51diplompersonaltrainerrest.mapper;

import com.example.c51diplompersonaltrainerrest.dto.ShopDTO;
import com.example.c51diplompersonaltrainerrest.entity.Shop;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopDTO shopToShopDTO(Shop shop);
    Shop shopDTOToShop(ShopDTO shopDTO);
}
