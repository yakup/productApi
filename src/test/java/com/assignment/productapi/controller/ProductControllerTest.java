package com.assignment.productapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.service.ProductService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testProductNotFound() throws Exception {
        when(productService.findProducts("subscription", null, null, null, null, null, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/product").param("type", "subscription")).andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    public void testUnexpectedError() throws Exception {
        when(productService.findProducts("subscription", null, null, null, null, null, null)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/product").param("type", "subscription")).andExpect(status().isInternalServerError())
                .andExpect(content().string("Unexpected error occurred"));
    }

    @Test
    public void testGetProductWithColorProperty() throws Exception {
        Product product = new Product();
        product.setType("subscription");
        product.setColor("Red");
        product.setPrice(99.0);
        product.setStoreAddress("Dana gärdet, Stockholm");

        when(productService.findProducts("subscription", null, null, null, null, null, null)).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/product").param("type", "subscription")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("subscription"))
                .andExpect(jsonPath("$[0].properties").value("color:Red"))
                .andExpect(jsonPath("$[0].price").value("99.0"))
                .andExpect(jsonPath("$[0].storeAddress").value("Dana gärdet, Stockholm"));
    }

    @Test
    public void testGetProductWithGbLimitProperty() throws Exception {
        Product product = new Product();
        product.setType("subscription");
        product.setGbLimit(9);
        product.setPrice(99.0);
        product.setStoreAddress("Dana gärdet, Stockholm");

        when(productService.findProducts("subscription", null, null, null, null, null, null)).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/product").param("type", "subscription")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("subscription"))
                .andExpect(jsonPath("$[0].properties").value("gb_limit:9"))
                .andExpect(jsonPath("$[0].price").value("99.0"))
                .andExpect(jsonPath("$[0].storeAddress").value("Dana gärdet, Stockholm"));
    }
}
