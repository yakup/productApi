package com.assignment.productapi.service;

import com.assignment.productapi.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> findProducts(String type, String city, Integer minPrice, Integer maxPrice, String color, Integer gbLimitMin, Integer gbLimitMax);
}
