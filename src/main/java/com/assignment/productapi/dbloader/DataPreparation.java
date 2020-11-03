package com.assignment.productapi.dbloader;

import com.assignment.productapi.model.Product;
import com.assignment.productapi.repository.ProductRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataPreparation {
    private final ProductRepository productRepository;

    public DataPreparation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
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

        InputStream inputStream = getClass().getResourceAsStream("/data/data.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        CsvToBean cb = new CsvToBeanBuilder<ProductData>(inputStreamReader)
                .withType(ProductData.class)
                .withMappingStrategy(ms)
                .build();

        List<ProductData> parse = cb.parse();
        inputStreamReader.close();
        return parse;
    }
}
