package com.assignment.productapi.controller.dto.converter;

import com.assignment.productapi.controller.dto.ProductDto;
import com.assignment.productapi.model.Product;
import org.springframework.util.StringUtils;

public class ProductDtoConverter {

    public ProductDto productDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setType(product.getType());
        dto.setPrice(product.getPrice());
        dto.setStoreAddress(product.getStoreAddress());

        if (!StringUtils.isEmpty(product.getColor()))
            dto.setProperties("color:" + product.getColor());
        else if (product.getGbLimit() != null) {
            dto.setProperties("gb_limit:" + product.getGbLimit());
        }

        return dto;
    }
}
