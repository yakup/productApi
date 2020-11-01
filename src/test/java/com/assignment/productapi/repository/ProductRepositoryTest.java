package com.assignment.productapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.assignment.productapi.model.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    public void prepareData() {
        Product product1 = new Product();
        product1.setPrice(99.00);
        product1.setGbLimit(9);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setPrice(100.00);
        product2.setGbLimit(10);
        productRepository.save(product2);
    }

    @Test
    public void testGetProductByType() {
        Product product = new Product();
        product.setType("subscription");

        productRepository.save(product);

        List<Product> products = productRepository.findProducts("subscription", null, null, null, null, null, null);
        assertThat(products.size()).isEqualTo(1);

        Product savedProduct = products.get(0);
        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    public void testGetProductByCity() {
        Product product = new Product();
        product.setStoreAddress("Nilsson gatan, Stockholm");

        productRepository.save(product);

        List<Product> products = productRepository.findProducts(null, "Stockholm", null, null, null, null, null);
        assertThat(products.size()).isEqualTo(1);

        Product savedProduct = products.get(0);
        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    public void testGetProductByMinPrice() {
        List<Product> products = productRepository.findProducts(null, null, 100, null, null, null, null);
        assertThat(products.size()).isEqualTo(1);

        Product product = products.get(0);
        assertThat(product.getPrice()).isEqualTo(100);
    }

    @Test
    public void testGetProductByMaxPrice() {
        List<Product> products = productRepository.findProducts(null, null, null, 99, null, null, null);
        assertThat(products.size()).isEqualTo(1);

        Product savedProduct = products.get(0);
        assertThat(savedProduct.getPrice()).isEqualTo(99);
    }

    @Test
    public void testGetProductByColor() {
        Product product = new Product();
        product.setColor("Red");

        productRepository.save(product);

        List<Product> products = productRepository.findProducts(null, null, null, null, "Red", null, null);
        assertThat(products.size()).isEqualTo(1);

        Product savedProduct = products.get(0);
        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    public void testGetProductByMinGbLimit() {
        List<Product> products = productRepository.findProducts(null, null, null, null, null, 10, null);
        assertThat(1).isEqualTo(products.size());

        Product product = products.get(0);
        assertThat(product.getGbLimit()).isEqualTo(10);
    }

    @Test
    public void testGetProductByMaxGbLimit() {
        List<Product> products = productRepository.findProducts(null, null, null, null, null, null, 9);
        assertThat(1).isEqualTo(products.size());

        Product product = products.get(0);
        assertThat(product.getGbLimit()).isEqualTo(9);
    }
}
