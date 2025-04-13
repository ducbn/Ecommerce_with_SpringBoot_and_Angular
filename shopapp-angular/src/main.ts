import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { HomeComponent } from './app/home/home.component';
import { OrderComponent } from './app/order/order.component';
import { OrderConfirmComponent } from './app/order-confirm/order-confirm.component';
import { LoginComponent } from './app/login/login.component';
import { RegisterComponent } from './app/register/register.component';
import { DetailProductComponent } from './app/detail-product/detail-product.component';
import { FormsModule } from '@angular/forms'
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

bootstrapApplication(LoginComponent, {
  providers: [
    provideHttpClient(), // Cung cấp HttpClient
    provideRouter([]), // Thêm router nếu cần, hiện tại để mảng rỗng
  ],
}).catch((err) => console.error(err));
