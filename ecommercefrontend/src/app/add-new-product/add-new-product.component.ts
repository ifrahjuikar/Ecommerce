import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FileHandle } from '../_model/file-handle-model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-new-product',
  templateUrl: './add-new-product.component.html',
  styleUrls: ['./add-new-product.component.css']
})

export class AddNewProductComponent implements OnInit {
  // Product object initialized with default values
  product: Product = {
    productName: "",
    productDescription: "",
    productDiscountPrice: 0,
    productActualPrice: 0,
    productId: 0,
    productImages:[]
  };

  // Dependency injection of ProductService and DomSanitizer
  constructor(private productService: ProductService, private sanitizer: DomSanitizer, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    // this.product=this.activatedRoute.snapshot.data['product'];
  }

  // Array to store selected file handles
  selectedFiles: FileHandle[] = [];

  // Method to handle file selection from file input
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.selectedFiles = []; // Clear previously selected files if needed
      Array.from(input.files).forEach(file => {
        // Create a FileHandle object with a URL for previewing the file
        const fileHandle: FileHandle = {
          file: file,
          url: this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file))
        };
        this.selectedFiles.push(fileHandle);
      });

      // Add all selected file handles to productImages
      this.product.productImages.push(...this.selectedFiles);
    }
  }

  // Method to add a new product
  addProduct(productForm: NgForm) {
    // Prepare FormData object with product data and images
    const productFormData = this.prepareFormData(this.product);
    console.log(this.product); // Log the product object
    // Call the service to add the product
    this.productService.addProduct(productFormData).subscribe({
      next: (response: Product) => {
        console.log(response); // Log the response from the server
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error adding product:', error); // Log any errors
      },
      complete: () => {
        console.log('Product addition complete'); // Log when the request is complete
      }
    });
  }

  // Method to prepare FormData for sending product data and images
  prepareFormData(product: Product): FormData {
    const formData = new FormData();
    // Append product data as a JSON blob
    formData.append(
      'product',
      new Blob([JSON.stringify(product)], { type: 'application/json' })
    );
    // Append each image file to the FormData
    for (var i = 0; i < product.productImages.length; i++) {
      formData.append(
        'imageFile',
        product.productImages[i].file,
        product.productImages[i].file.name
      );
    }
    return formData;
  }

  // Method to remove an image from the productImages array
  removeImages(i: number) {
    this.product.productImages.splice(i, 1);
  }

  // Method to handle file drop events (if drag-and-drop functionality is implemented)
  fileDropped(fileHandle: FileHandle) {
    this.product.productImages.push(fileHandle);
  }
}
