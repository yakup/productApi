package com.assignment.productapi.dbloader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(properties = {"spring.profiles.active=prod"})
public class DbLoaderTest {

    @MockBean
    private ProductRepository productRepository;

    @SpyBean
    private DataPreparation dataPreparation;

    @Test
    public void testDataParse() throws IOException, URISyntaxException {
        verify(productRepository, times(3)).save(any(Product.class));
    }
}
