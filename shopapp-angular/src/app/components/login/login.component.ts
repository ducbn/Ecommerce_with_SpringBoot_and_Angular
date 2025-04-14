import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { LoginDTO } from '../../dtos/user/login.dto';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-login',
  imports: [HeaderComponent, FormsModule, CommonModule, FooterComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string = '0987654321';
  password: string = '12345678';

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService) {
    this.phoneNumber;
    this.password;
  }

  ngOnInit() {
    debugger
    this.roleService.getRoles().subscribe({
      next: (response) => {
        console.log(response);
      }
    });
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
      next: (response: LoginResponse) => {
        debugger
        const { token } = response;
        this.tokenService.setToken(token);
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
        alert(`Cannot register, error: ${error.error}`);
      },
    });
  }
}
