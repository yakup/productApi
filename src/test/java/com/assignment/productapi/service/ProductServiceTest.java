package com.assignment.productapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void testFindProducts() {
        Product product = new Product();
        product.setType("subscription");

        when(productRepository.findProducts("subscription", null, null, null, null, null, null)).thenReturn(Arrays.asList(product));

        List<Product> products = productService.findProducts("subscription", null, null, null, null, null, null);
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0)).isSameAs(product);
    }
}
