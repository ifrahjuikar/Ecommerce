package com.springbootproject.springbootproject.Entitities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @Temporal(TemporalType.DATE) // Field should only store the date part without any time information.
    private Date billDate;

    @ManyToOne
    @JoinColumn(name = "customerId") // Joining with Customer entity using customerId column
    private Customer customer; // Associated customer for this bill

    private Double billAmount; // Total amount of the bill

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<BillProduct> billProducts; // List of products in this bill

}
