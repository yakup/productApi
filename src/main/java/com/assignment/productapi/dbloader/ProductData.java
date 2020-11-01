package com.assignment.productapi.dbloader;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class ProductData {
    @CsvBindByPosition(position = 0)
    private String type;
    @CsvBindByPosition(position = 1)
    private String properties;
    @CsvBindByPosition(position = 2)
    private Double price;
    @CsvBindByPosition(position = 3)
    private String storeAddress;
}
