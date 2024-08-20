import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { ShowProductDetailsComponent } from './show-product-details/show-product-details.component';
import { ShowProductImagesDialogComponent } from './show-product-images-dialog/show-product-images-dialog.component';
import { ProductResolveService } from './product-resolve.service';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path:'header',
    component:HeaderComponent
  },
  {
    path: 'AddNewProduct', component: AddNewProductComponent,
    resolve:{
      product: ProductResolveService
    }
  },
  {
    path: 'ShowProductDetails', component: ShowProductDetailsComponent
  },
  {
    path: 'ShowProductImagesDialog', component: ShowProductImagesDialogComponent

  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
