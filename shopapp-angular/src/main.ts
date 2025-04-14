import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { HomeComponent } from './app/components/home/home.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { FormsModule } from '@angular/forms'
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './app/interceptors/token.interceptor';

bootstrapApplication(LoginComponent, {
  providers: [
    provideHttpClient(), // Cung cấp HttpClient
    provideRouter([]), // Thêm router nếu cần, hiện tại để mảng rỗng
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
  ],
}).catch((err) => console.error(err));
