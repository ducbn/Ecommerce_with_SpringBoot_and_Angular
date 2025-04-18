import { HttpParams } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Category } from '../models/category';

@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    private apiGetCategories = `${environment.apiUrl}/categories`;
    
    constructor(private http: HttpClient) {}
    
    getCategories(page: number, limit: number): Observable<Category[]> {
        let params = new HttpParams()
            .set('page', page.toString())
            .set('limit', limit.toString());
    
        return this.http.get<Category[]>(this.apiGetCategories, { params });
    }
}