package com.springbootproject.springbootproject.Entitities;

import java.util.Set;

import javax.persistence.CascadeType;

// import java.io.Serializable;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// @Table(name="product") // Specifies the name of the table in the database
// public class Product implements Serializable {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the generation strategy for the primary key
//     @Column(name="product_id") // Specifies the name of the column in the database
//      // Primary key for the Product entity
//     private Long productId;

//     @Column(nullable=false,name="product_name") // Specifies the column properties
//     // Name of the product
//     private String productName; 

//     @Column(name="quantity_available") // Specifies the name of the column in the database
//     // Quantity available of the product
//     private int quantityAvailable; 

//     @Column(nullable = false, name="product_price") // Specifies the column properties
//     // Price of the product
//     private double productPrice; 
// }

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    private String productName;
    private String productDescription;
    private Double productDiscountPrice;
    private Double productActualPrice;

    // binding imagmodel class..set is use as there can be many images for the
    // product
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_images", joinColumns = {
            @JoinColumn(name = "product_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "image_id")
    })
    private Set<ImageModel> productImages;
}