import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { Product } from '../_model/product.model';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';
import { ImageProcessingService } from '../image-processing.service';
import { map } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-product-details',
  templateUrl: './show-product-details.component.html',
  styleUrls: ['./show-product-details.component.css']
})
export class ShowProductDetailsComponent implements OnInit {

  // Initializes an empty array to hold Product objects. This array will be used as the data source for the Angular Material table.
  productDetails: Product[] = [];

  // Defines an array of column names that will be displayed in the table. Each string corresponds to a column header in the table created in html file.
  displayedColumns: string[] = ['Id', 'Product Name', 'Product Description', 'Product Discount Price', 'Product Actual Price', 'Images','Edit', 'Delete'];

  //Dependency injection of ProductService into the component. This service will be used to fetch product data.
  constructor(private productService: ProductService, public imagesDialog: MatDialog ,  private imageProessingService: ImageProcessingService, private router:Router) { }

  ngOnInit(): void {
    // it calls getAllProducts() to fetch data when the component is initialized of this file.
    this.getAllProducts();
  }

  public getAllProducts() {
    // A public method that fetches products from the ProductService
    this.productService.getAllProducts()

    .subscribe(
      // The callback function that handles the response. It assumes resp is an array of Product
      (resp: Product[]) => {
        console.log('API Response:', resp); // Log the response
        console.log('Is array:', Array.isArray(resp)); // Check if it's an array
        // Converts the response into an array of its values.
        const valuesArray = Object.values(resp);
        console.log(valuesArray[3]);

        if (Array.isArray(valuesArray)) {
          // Assigns the value of valuesArray[3] to productDetails. This assumes that valuesArray[3] is an array of products. 
          this.productDetails = Object.values(valuesArray[3]);
          console.log(this.productDetails);
        
        } else {
          // Logs an error if valuesArray is not an array.
          console.error('Expected an array but got:', typeof resp);
        }
        this.productDetails = this.productDetails.map((product: Product) => 
                  this.imageProessingService.createImages(product)
                );
      },
      (error: HttpErrorResponse) => {
        // The callback function that handles errors from the API request.
        console.log('HTTP Error:', error);
      }
    );
  }

  // public getAllProducts() {
  //   this.productService.getAllProducts()
  //     .subscribe(
  //       (resp: Product[]) => {
  //         console.log('API Response:', resp);
  //         console.log('Is array:', Array.isArray(resp));
  
  //         if (Array.isArray(resp)) {
  //           // If resp is already an array of Products, we don't need to use Object.values
  //           this.productDetails = resp;
  //         } else if (typeof resp === 'object' && resp !== null) {
  //           // If resp is an object, convert it to an array of its values
  //           this.productDetails = Object.values(resp);
  //         } else {
  //           console.error('Unexpected response type:', typeof resp);
  //           return;
  //         }
  
  //         console.log(this.productDetails);
  
  //         // Process each product in the array
  //         this.productDetails = this.productDetails.map((product: Product) => 
  //           this.imageProessingService.createImages(product)
  //         );
  //       },
  //       (error: HttpErrorResponse) => {
  //         console.log('HTTP Error:', error);
  //       }
  //     );
  // }

  deleteProduct(productId: number): void {  // Specify type for productId
    console.log(productId);
    this.productService.deleteProduct(productId)
    .subscribe(
      (resp: any) => {  // Specify type for response
        console.log(resp);
        // Remove the deleted product from the productDetails array
        this.productDetails = this.productDetails.filter(product => product.productId !== productId);
        console.log('Updated product list:', this.productDetails);
        console.log(resp);
      },
      (error: HttpErrorResponse) => {  // Specify type for error
        console.log(error);
      }
    );


  }

  showImages(product:Product){
    console.log(product);
    this.imagesDialog.open(ShowProductImagesDialogComponent,{
      data:{
        images: product.productImages
      },
      height:'500px',
      width:'800px'
    })
      
        
      
  }

  editProductDetails(productId: number){
    console.log(productId);
    this.router.navigate(['/AddNewProduct', {productId:productId}])
  }
}
















//extra logic


// public getAllProducts() {
//   this.productService.getAllProducts().subscribe(
//     (response: Product[]) => {
//       this.productDetails = response;
//     },
//     (error: HttpErrorResponse) => {
//       console.log('HTTP Error:', error);
//     }
//   );
// }

// public setAllProduct(){
//   const prodList = document.getElementById("pid");
//   if (prodList){
//     this.productDetails.forEach(product => {
//       const listItem = document.createElement('li');
//       console.log(product)
//       listItem.textContent = "${product.productId}";
//       prodList.appendChild(listItem);
//     });
//   }
// }



// function deprecated(target: ShowProductDetailsComponent, propertyKey: 'getAllProducts', descriptor: TypedPropertyDescriptor<() => void>): void | TypedPropertyDescriptor<() => void> {
//   throw new Error('Function not implemented.');
// }

