import { ProductService } from "./product.service";
import { Injectable } from '@angular/core';
import { Product } from "../models/product";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cart: Map<number, number> = new Map(); // lưu productId -> quantity

    constructor(private productsService: ProductService) {
        if (typeof window !== 'undefined') {
            const storedCart = localStorage.getItem('cart');
            if (storedCart) {
                this.cart = new Map(JSON.parse(storedCart));
            }
        }
    }

    addToCart(productId: number, quantity: number = 1): void {
        debugger
        if (this.cart.has(productId)) {
            this.cart.set(productId, this.cart.get(productId)! + quantity);
        } else {
            this.cart.set(productId, quantity);
        }
        this.saveCartToLocalStorage();
    }

    getCart(): Map<number, number> {
        return this.cart;
    }

    private saveCartToLocalStorage(): void {
        debugger
        if (typeof window !== 'undefined') {
            localStorage.setItem('cart', JSON.stringify(Array.from(this.cart.entries())));
        }
    }

    clearCart(): void {
        this.cart.clear();
        this.saveCartToLocalStorage();
    }
}
