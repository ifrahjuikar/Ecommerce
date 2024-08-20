import { FileHandle } from "./file-handle-model";

export interface Product{
    productActualPrice:number,
    productDescription:String,
    productDiscountPrice: number,
    productId : number,
    productImages: FileHandle[],
    productName:String
}