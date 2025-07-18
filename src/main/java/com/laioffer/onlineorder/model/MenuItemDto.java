package com.laioffer.onlineorder.model;


import com.laioffer.onlineorder.entity.MenuItemEntity;

// Doesn't include restaurant ID
    // "Single source of truth"
public record MenuItemDto(
        Long id,
        String name,
        String description,
        Double price,
        String imageUrl
) {


    public MenuItemDto(MenuItemEntity entity) {
        this(entity.id(), entity.name(), entity.description(), entity.price(), entity.imageUrl());
    }
}
