package com.springbootproject.springbootproject.ServiceImplementation;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springbootproject.springbootproject.Entitities.Cart;
import com.springbootproject.springbootproject.Entitities.Product;
import com.springbootproject.springbootproject.Entitities.ProductShadow;
import com.springbootproject.springbootproject.Entitities.User;
import com.springbootproject.springbootproject.Exception.ProductNotFoundException;
import com.springbootproject.springbootproject.Jwt.JwtAuthenticationFilter;
import com.springbootproject.springbootproject.Repositories.CartRepository;
import com.springbootproject.springbootproject.Repositories.ProductRepository;
import com.springbootproject.springbootproject.Repositories.UserRepository;
// import com.springbootproject.springbootproject.Repositories.ProductShadowRepository;
import com.springbootproject.springbootproject.Service.ProductService;

/**
 * Service implementation class for handling product-related operations.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    // @Autowired
    // private ProductShadowRepository productShadowRepository;

    /**
     * Adds a new product.
     * 
     * @param product The product to be added.
     * @return The added product.
     */
    @Override
    public Product addProduct(Product product) {
        // Adding a new product
        Product addedProduct = productRepository.save(product);
        return addedProduct;
    }

    /**
     * Updates an existing product.
     * 
     * @param product The product with updated details.
     * @return The updated product.
     */
    @Override
    public Product updateProduct(Product product) {
        // Updating an existing product
        Integer productId = product.getProductId();
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with product id: " + productId));

        // copyProductToShadow(existingProduct); // Copying product details to shadow table

        // Updating product details
        if (product.getProductName() != null) {
            existingProduct.setProductName(product.getProductName());
        }
        if (product.getProductActualPrice() != 0) {
            existingProduct.setProductActualPrice(product.getProductActualPrice());
        }
        if (product.getProductDiscountPrice() != 0) {
            existingProduct.setProductDiscountPrice(product.getProductDiscountPrice());
        }

        // Saving the updated product details
        return productRepository.save(existingProduct);
    }

    /**
     * Deletes a product.
     * 
     * @param productId The ID of the product to be deleted.
     * @return The deleted product.
     */
    @Override
    public Product deleteProduct(Integer productId) {
        // Deleting a product
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with product id: " + productId));

        // copyProductToShadow(existingProduct); // Copying product details to shadow table

        // Deleting the product
        productRepository.delete(existingProduct);

        return existingProduct;
    }

    // /**
    //  * Retrieves all products.
    //  * 
    //  * @return A list of all products.
    //  */
    // @Override
    // public List<Product> getAllProducts() {
    //     Pageable pageable=PageRequest.of(0, 10);

    //     // Retrieving all products
    //     List<Product> products = productRepository.findAll(pageable);
        
    //     return products;
    // }




//second approach

@Override
public List<Product> getAllProducts(int pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, 2);

    // Retrieving all products as a Page
    Page<Product> productPage = productRepository.findAll(pageable);
    
    // Convert Page<Product> to List<Product>
    List<Product> products = productPage.getContent();
    
    return products;
}

@Override
public List<Product> getAllProducts() {
    Pageable pageable = PageRequest.of(0, 1);

    // Retrieving all products as a Page
    Page<Product> productPage = productRepository.findAll(pageable);
    
    // Convert Page<Product> to List<Product>
    List<Product> products = productPage.getContent();
    
    return products;
}


@Override
public List<Product> getAllProducts(int pageNumber, String searchKey) {
    Pageable pageable = PageRequest.of(pageNumber, 2);

   
    // Retrieving all products as a Page
    Page<Product> productPage = productRepository.findAll(pageable);
    
    // Convert Page<Product> to List<Product>
    List<Product> products = productPage.getContent();

    if(searchKey.equals("")){
        return products;
    }else{
        return productRepository.searchProducts(searchKey, pageable);
        // return productRepository.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey,pageable);
    }

    
}

// @Override
// public Page<Product> getAllProducts(int pageNumber, String searchKey) {
//     Pageable pageable = PageRequest.of(pageNumber, 10);
//     if(searchKey.equals("")){
//         return productRepository.findAll(pageable);
//     } else {
//         return productRepository.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
//     }
// }

    

//




    /**
     * Retrieves a product by ID.
     * 
     * @param productId The ID of the product to be retrieved.
     * @return The product with the specified ID.
     */
    @Override
    public Product getProductById(Integer productId) {
        // Retrieving a product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with product id: " + productId));
        return product;
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if(isSingleProductCheckout){
            //buy single product
            List<Product> list=new ArrayList<>();
            Product product = productRepository.findById(productId).get();
            list.add(product);
            return list;

        }else{
           String username=JwtAuthenticationFilter.CURRENT_USER;
           User user=userRepository.findById(username).get();
           List<Cart> carts=cartRepository.findByUser(user);
           return carts.stream().map(x->x.getProduct()).collect(Collectors.toList());

        }

    }



    /**
     * Copies product details to the shadow table.
     * 
     * @param product The product whose details are to be copied.
     */

    // private void copyProductToShadow(Product product) {
    //     ProductShadow productShadow = new ProductShadow();
    //     productShadow.setProductId(String.valueOf(product.getProductId()));
    //     productShadow.setProductName(product.getProductName());
    //     productShadow.setProductPrice(String.valueOf(product.getProductActualPrice()));
    //     productShadow.setQuantityAvailable(String.valueOf(product.getProductDiscountPrice()));
    //     productShadowRepository.save(productShadow);
    // }

    /**
     * Generates a PDF containing details of multiple products.
     * 
     * @param productList A list of products to include in the PDF.
     * @param response    The HttpServletResponse object to write the PDF to.
     * @throws DocumentException Thrown if there is an error while generating the
     *                           PDF document.
     * @throws IOException       Thrown if there is an error while writing the PDF
     *                           to the HttpServletResponse.
     */
    @Override
    public void generateProductPdf(List<Product> productList, ByteArrayOutputStream outputStream)
            throws IOException {
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            addProductDetailsToPdf(document, productList);
        }
    }

    /**
     * Adds product details to the PDF document.
     *
     * @param document    The PDF document to which the product details will be added.
     * @param productList The list of products containing details to be added.
     * @throws DocumentException If an error occurs while adding content to the document.
     */
    private void addProductDetailsToPdf(Document document, List<Product> productList)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4); // Create a table with 4 columns
        table.setWidthPercentage(100); // Set table width to 100% of the page widht
        table.setWidths(new float[]{1, 3, 3, 2});// Set relative column widths
        table.setSpacingBefore(10);
        addTableHeader(table);
        for (Product product : productList) {
            table.addCell(String.valueOf(product.getProductId()));
            table.addCell(product.getProductName());
            table.addCell(String.valueOf(product.getProductActualPrice()));
            table.addCell(String.valueOf(product.getProductDiscountPrice()));
        }
        document.add(table);
    }

     /**
     * Adds table header with specified column headers to the given PdfPTable.
     *
     * @param table The PdfPTable to which the header will be added.
     */
    private void addTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE); // Change color using OpenPDF Color class
        cell.setPadding(5);
        String[] headers = {"Product ID", "Product Name", "Quantity Available", "Product Price"};
        for (String header : headers) {
            cell.setPhrase(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE))); // Use OpenPDF Font and Color classes
            table.addCell(cell);
        }
    }
}


