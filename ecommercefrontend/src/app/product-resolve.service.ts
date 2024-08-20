import { Injectable } from '@angular/core';
import { Product } from './_model/product.model';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, map } from 'rxjs';
import { ProductService } from './_services/product.service';
import { ImageProcessingService } from './image-processing.service';
import { of } from 'rxjs';
@Injectable({
  providedIn: 'root'
})

export class ProductResolveService implements Resolve<Product>{

  constructor(private productSevice:ProductService, private imageProcessingService:ImageProcessingService) { }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> {
    const id = route.paramMap.get("productId");

    if (id) {
      return this.productSevice.getProductDetailsById(id)
      .pipe(
        map(p=>this.imageProcessingService.createImages(p))
      )
      ;

    } else{
      return of(this.getProductDetails());
    }

  }

  getProductDetails() {
    return {
      productActualPrice: 0,
      productDescription: "",
      productDiscountPrice: 0,
      productId: 0,
      productImages: [],
      productName: ""
    }

  }

}
