import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Product } from '../models/product';

@Injectable({
    providedIn: 'root',
})
export class ProductService {
    private apiGetProducts = `${environment.apiUrl}/products`;

    constructor(private http: HttpClient) { }

    getProducts(
        keyword: string,
        categoryId: number,
        page: number,
        limit: number
    ): Observable<Product[]> {
        const params = new HttpParams()
            .set('keyword', keyword)
            .set('category_id', categoryId.toString())
            .set('page', page.toString())
            .set('limit', limit.toString());
        return this.http.get<Product[]>(this.apiGetProducts, { params });
    }

    getDetailProduct(productId: number){
        return this.http.get(`${environment.apiUrl}/products/${productId}`);
    }

    getProductsByIds(productIds: number[]): Observable<Product[]> {
        //chuyển đổi mảng productIds thành chuỗi
        // ví dụ: [1, 2, 3] -> "1,2,3"
        const params = new HttpParams().set('ids', productIds.join(','));
        return this.http.get<Product[]>(`${this.apiGetProducts}/by-ids`, { params });
    }
}
