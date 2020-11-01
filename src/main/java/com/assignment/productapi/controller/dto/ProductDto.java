package com.assignment.productapi.controller.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String type;
    private String properties;
    private Double price;
    private String storeAddress;
}
