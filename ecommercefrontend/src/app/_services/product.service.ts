import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Product } from '../_model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/product';  // API endpoint

  constructor(private httpClient: HttpClient) { }

  public addProduct(product: FormData): Observable<Product> {
    return this.httpClient.post<Product>(this.apiUrl, product);
  }

  public getAllProducts(){
    return this.httpClient.get<Product[]>("http://localhost:8080/products");
  }

  public deleteProduct(productId: number): Observable<any> {  // Return an Observable
    return this.httpClient.delete(`${this.apiUrl}/${productId}`);
  }
  // getAllProducts(): Observable<Product[]> {
  //   return this.httpClient.get<{ products: Product[] }>("http://localhost:8080/products")
  //     .pipe(
  //       map(response => response.products) // Extract the products array
  //     );
  // }
  public getProductDetailsById(productId: String): Observable<Product> {
    return this.httpClient.get<Product>(`${this.apiUrl}/${productId}`);
  }
}
