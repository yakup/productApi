package com.assignment.productapi.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    public void populateData() {
        RestAssured.port = port;

        Product product1 = new Product();
        product1.setType("subscription");
        product1.setPrice(99.00);
        product1.setGbLimit(9);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setType("subscription");
        product2.setPrice(100.00);
        product2.setGbLimit(10);
        productRepository.save(product2);
    }

    @Test
    public void testProductNotFound() {
        given().
                when().get("/product?min_price=200").
                then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testProductTypeSubscription() {
        Response response = given().
                when().get("/product?type=subscription").
                then().statusCode(HttpStatus.OK.value())
                .extract().response().andReturn();

        List<String> list = response.jsonPath().getList("$");
        assertThat(list.size()).isEqualTo(2);
    }
}
