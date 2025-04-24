import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { Product } from '../../models/product';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { environment } from '../../environments/environment';
import { Form, FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrderDTO } from '../../models/order.dto';
import { OrderService } from '../../services/order.service';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-order',
  imports: [FooterComponent, HeaderComponent, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit {
  orderForm: FormGroup;
  cartItems: { product: Product, quantity: number }[] = [];
  totalAmount: number = 0;
  couponCode: string = '';//mã giảm giá
  orderData: OrderDTO = {
    user_id: 1,
    fulname: '',
    email: '',
    phone_number: '',
    address: '',
    note: '',
    total_money: 0,
    payment_method: 'cod',
    shipping_method: 'express',
    coupon_code: '',
    cart_items: []
  }

  constructor(
      private cartService: CartService,
      private productService: ProductService,
      private orderService: OrderService,
      private fb: FormBuilder
    ) 
    {  
      this.orderForm = this.fb.group({
        fullname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', [Validators.required, Validators.minLength(10)]],
        address: ['', [Validators.required, Validators.minLength(10)]],
        note: [''],
        couponCode: [''],
        payment: ['cod'],
        shipping: ['express']
      });
      
    }

    ngOnInit() {
      debugger
      // lấy danh sách sản phẩm trong giỏ hàng
      const cart = this.cartService.getCart();
      const productIds = Array.from(cart.keys()); // chuyển ds Id từ Map sang Array
      
      //gọi service lấy danh sách sản phẩm theo id
      debugger
      this.productService.getProductsByIds(productIds).subscribe({
        next: (products) => {
          debugger
          //lấy thông tin sản phaamt và số lượng từ ds giỏ hàng và giỏ hàng
          this.cartItems = productIds.map((productId) => {
            debugger
            const product = products.find((p) => p.id === productId);
            if (product){
              product.thumbnail = `${environment.apiUrl}/products/images/${product.thumbnail}`;
            }
            return {
              product: product!,
              quantity: cart.get(productId)!
            };
          });
          console.log("ok");
        },
        complete: () => {
          debugger
          console.log('Completed fetching products');
          this.caculateTotal();
        },
        error: (error) => {
          debugger
          console.error('Error fetching products:', error);
        }
  
      })
  
    }
    
    placeOrder(){
      debugger
      if (this.orderForm.valid) {
        this.orderData.fulname = this.orderForm.get('fullname')!.value;
        this.orderData.email = this.orderForm.get('email')!.value;
        this.orderData.phone_number = this.orderForm.get('phone')!.value;
        this.orderData.address = this.orderForm.get('address')!.value;
        this.orderData.note = this.orderForm.get('note')!.value;
        this.orderData.payment_method = this.orderForm.get('payment')!.value;
        this.orderData.shipping_method = this.orderForm.get('shipping')!.value;
      
      
      this.orderService.placeOrder(this.orderData).subscribe({
        next: (response) => {
          console.log('Order placed successfully:', response);
          // Xử lý phản hồi từ server nếu cần
        },
        complete: () => {
          console.log('Order placement completed');
          this.caculateTotal();
        },
        error: (error: any) => {
          console.error('Error placing order:', error);
          // Xử lý lỗi nếu có
        }
      });
      } else {
        alert('Vui lòng điền lại thông tin');
      }
    }

    //hàm tính tổng tiền
    caculateTotal(): void {
      this.totalAmount = this.cartItems.reduce(
        (total, item) => total + item.product.price * item.quantity,
        0
      );
    }
  
    applyCoupon(): void {
    }
}
