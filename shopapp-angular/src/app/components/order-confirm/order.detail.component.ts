import { Component } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { environment } from '../../environments/environment';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderResponse } from '../../responses/order/order.response';
import { OrderService } from '../../services/order.service';
import { OrderDetail } from '../../models/order.detail';


@Component({
  selector: 'app-order-detail',
  imports: [FooterComponent, HeaderComponent, CommonModule, FormsModule],
  templateUrl: './order.detail.component.html',
  styleUrl: './order.detail.component.css'
})
export class OrderDetailComponent {
  orderResponse: OrderResponse = {
    id: 0,
    user_id: 0,
    fullname: '',
    email: '',
    phone_number: '',
    address: '',
    note: '',
    order_date: new Date(),
    status: '',
    total_money: 0,
    shipping_method: '',
    shipping_address: '',
    shipping_date: new Date(),
    payment_method: '',
    order_details: []
  }

  constructor(private orderService: OrderService){
  }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(): void {
    debugger
    const orderId = 9; // Replace with the actual order ID you want to fetch
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: any) => {
        debugger
        this.orderResponse.id = response.id;
        this.orderResponse.user_id = response.user_id;
        this.orderResponse.fullname = response.fullname;
        this.orderResponse.email = response.email;
        this.orderResponse.phone_number = response.phone_number;
        this.orderResponse.address = response.address;
        this.orderResponse.note = response.note;
        this.orderResponse.order_date = new Date(
          response.order_date[0],
          response.order_date[1] - 1,
          response.order_date[2],
        );
        debugger
        this.orderResponse.order_details = response.order_details.map((order_detail: OrderDetail) => {
          order_detail.product.thumbnail = `${environment.apiUrl}/products/images/${order_detail.product.thumbnail}`;
          return order_detail;
        });
        this.orderResponse.payment_method = response.payment_method;
        this.orderResponse.shipping_date = new Date(
          response.shipping_date[0],
          response.shipping_date[1] - 1,
          response.shipping_date[2],
        );
        this.orderResponse.shipping_method = response.shipping_method;
        this.orderResponse.status = response.status;
        this.orderResponse.total_money = response.total_money;
      },
      complete: () => {
        debugger
        console.log('Order details fetched successfully:', this.orderResponse);
      },
      error: (error: any) => {
        console.error('Error fetching order details:', error);
      }
    });
  }
}
