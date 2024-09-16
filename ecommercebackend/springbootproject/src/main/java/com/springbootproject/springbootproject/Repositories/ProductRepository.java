package com.springbootproject.springbootproject.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springbootproject.springbootproject.Entitities.Product;

// Repository interface for managing Product entities
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

     public Page<Product> findAll(Pageable pageable);

    //  public List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(String key1, String key2 , Pageable pageable);
    //  public Page<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(String key1, String key2, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchKey, '%')) " +
           "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<Product> searchProducts(@Param("searchKey") String searchKey, Pageable pageable);
    
}
