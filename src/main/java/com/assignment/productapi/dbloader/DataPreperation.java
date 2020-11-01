package com.assignment.productapi.dbloader;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataPreperation {
    private final ProductRepository productRepository;

    public DataPreperation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    public void postConstruct() throws IOException, URISyntaxException {
        if (!"test".equals(profile)) {
            insertCSVDataIntoDB();
        }
    }

    public void insertCSVDataIntoDB() throws IOException, URISyntaxException {
        List<ProductData> productDataList = getProductData();

        for (ProductData productData : productDataList) {
            Product product = new Product();
            product.setType(productData.getType());
            product.setPrice(productData.getPrice());
            product.setStoreAddress(productData.getStoreAddress());

            String[] properties = productData.getProperties().split(":");
            String propertyType = properties[0];
            String propertyValue = properties[1];
            switch (propertyType) {
                case "color":
                    product.setColor(propertyValue);
                    break;
                case "gb_limit":
                    product.setGbLimit(Integer.valueOf(propertyValue));
            }

            productRepository.save(product);
        }
    }

    private List<ProductData> getProductData() throws IOException, URISyntaxException {
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(ProductData.class);

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("data/data.csv").toURI()));
        CsvToBean cb = new CsvToBeanBuilder<ProductData>(reader)
                .withType(ProductData.class)
                .withMappingStrategy(ms)
                .build();

        List<ProductData> parse = cb.parse();
        reader.close();
        return parse;
    }
}
