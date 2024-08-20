import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

import { MatMenuModule } from '@angular/material/menu';  
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { ProductService } from './_services/product.service';
import { HttpClientModule } from '@angular/common/http';
import { MatGridListModule } from '@angular/material/grid-list';
import { DragDirective } from './drag.directive';
import { ShowProductDetailsComponent } from './show-product-details/show-product-details.component'
import { MatTableModule} from '@angular/material/table'
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { ShowProductImagesDialogComponent } from './show-product-images-dialog/show-product-images-dialog.component';
import { ImageProcessingService } from './image-processing.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    AddNewProductComponent,
    DragDirective,
    ShowProductDetailsComponent,
    ShowProductImagesDialogComponent
    ],
  imports: [
    BrowserModule,
    RouterOutlet,
    RouterLink,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    MatMenuModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule ,// Ensure HttpClientModule is imported here
    AppRoutingModule,
    MatGridListModule,
    MatTableModule, //For Creating Table
    MatButtonModule //For Button
  ],
  providers: [ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }
