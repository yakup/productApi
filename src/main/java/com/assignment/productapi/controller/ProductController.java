package com.assignment.productapi.controller;

import com.assignment.productapi.controller.dto.ProductDto;
import com.assignment.productapi.controller.dto.converter.ProductDtoConverter;
import com.assignment.productapi.model.Product;
import com.assignment.productapi.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getProducts(@RequestParam(required = false) String type,
                                              @RequestParam(required = false) String city,
                                              @RequestParam(name = "min_price", required = false) Integer minPrice,
                                              @RequestParam(name = "max_price", required = false) Integer maxPrice,
                                              @RequestParam(name = "property:color", required = false) String color,
                                              @RequestParam(name = "property:gb_limit_min", required = false) Integer gbLimitMin,
                                              @RequestParam(name = "property:gb_limit_max", required = false) Integer gbLimitMax) {
        List<Product> products;
        try {
            products = productService.findProducts(type, city, minPrice, maxPrice, color, gbLimitMin, gbLimitMax);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }

        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");

        return ResponseEntity.ok(convertProductDtoList(products));
    }

    private List<ProductDto> convertProductDtoList(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            productDtoList.add(new ProductDtoConverter().productDto(product));
        }

        return productDtoList;
    }
}
