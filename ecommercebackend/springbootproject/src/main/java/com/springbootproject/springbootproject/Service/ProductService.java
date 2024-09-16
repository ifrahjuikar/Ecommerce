package com.springbootproject.springbootproject.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.springbootproject.springbootproject.Entitities.Product;

// Service interface for managing Product entities
public interface ProductService {

    // Method to add a new product
    public Product addProduct(Product product);

    // Method to update an existing product
    public Product updateProduct(Product product);

    // Method to delete a product
    public Product deleteProduct(Integer productId);

    // Method to retrieve all products
    public List<Product> getAllProducts();

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId);
    
    // Method to retrieve a product by ID
    public Product getProductById(Integer productId);

    public List<Product> getAllProducts(int pageNumber);

    public List<Product> getAllProducts(int pageNumber, String searchKey);


    


    // Method to generate a PDF of product list
    public void generateProductPdf(List<Product> productList, ByteArrayOutputStream outputStream)
            throws IOException;

}
