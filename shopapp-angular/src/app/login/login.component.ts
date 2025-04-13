import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { LoginDTO } from '../dtos/user/login.dto';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [HeaderComponent,FormsModule, CommonModule, FooterComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string = '0987654321';
  password: string = '12345678';

  constructor(private router: Router, private userService: UserService) {
      this.phoneNumber;
      this.password;
  }

  onPhoneNumberChange() {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  login() {
    const loginDTO: LoginDTO = {
      "phone_number": this.phoneNumber,
      "password": this.password,
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: any) => {
        debugger
      },
      complete: () => { },
      error: (error: any) => {
        alert(`Cannot register, error: ${error.error}`);
      },
    });
  }
}
