package com.assignment.productapi.service.impl;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import com.assignment.productapi.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProducts(String type, String city, Integer minPrice, Integer maxPrice, String color, Integer gbLimitMin, Integer gbLimitMax) {
        return productRepository.findProducts(type, city, minPrice, maxPrice, color, gbLimitMin, gbLimitMax);
    }
}
