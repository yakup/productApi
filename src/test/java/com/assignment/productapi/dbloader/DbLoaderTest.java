package com.assignment.productapi.dbloader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DbLoaderTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private DataPreperation dataPreperation;

    @Test
    public void testDataParse() throws IOException, URISyntaxException {
        dataPreperation.insertCSVDataIntoDB();
        verify(productRepository, times(3)).save(any(Product.class));
    }
}
