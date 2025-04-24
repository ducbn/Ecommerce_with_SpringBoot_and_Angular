import { Product } from "./product";

export interface OrderDetail {
    id: number;
    order_id: number;
    product: Product;
    price: number;
    number_of_product: number;
    total_money: number;
    color: string;
}
