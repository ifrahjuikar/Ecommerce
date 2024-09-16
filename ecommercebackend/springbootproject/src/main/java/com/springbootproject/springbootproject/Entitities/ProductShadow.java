package com.springbootproject.springbootproject.Entitities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product_shadow")
// A shadow table same as that of product table.
public class ProductShadow implements Serializable {
    @Id
    @Column(name="product_id")
    private String productId;

    @Column(nullable=false,name="product_name")
    private String productName;

    @Column(name="quantity_available")
    private String quantityAvailable;

    @Column(nullable = false, name="product_price")
    private String productPrice;
}
