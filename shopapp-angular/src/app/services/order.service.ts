import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderDTO } from '../models/order.dto';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) {}

  placeOrder(orderData: OrderDTO): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, orderData);
  }

  getOrderById(orderId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${orderId}`);
  }
}
