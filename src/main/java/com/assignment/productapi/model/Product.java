package com.assignment.productapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String type;

    @Column
    private Double price;

    @Column
    private String storeAddress;

    @Column
    private String color;

    @Column
    private Integer gbLimit;

}
