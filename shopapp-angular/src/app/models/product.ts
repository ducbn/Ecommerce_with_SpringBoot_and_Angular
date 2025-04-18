import { Category } from "./category";
import { ProductImage } from "./product.image";

export interface Product {
  id: number;
  name: string;
  price: number;
  thumbnail: string;
  description: string;
  category: number;
  url: string;
  product_images: ProductImage[];
}
