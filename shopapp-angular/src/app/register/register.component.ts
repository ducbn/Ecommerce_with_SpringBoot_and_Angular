import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Thêm import này
import { FooterComponent } from '../footer/footer.component';
import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule, 
    CommonModule, 
    FooterComponent,
    HttpClientModule, // Thêm để hỗ trợ HttpClient
    RouterModule
  ], 
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;

  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;

  constructor(private http: HttpClient, private router: Router) {
    this.phone = '0987654321';
    this.password = '12345678';
    this.retypePassword = '12345678';
    this.fullName = 'Bui Ngoc Duc';
    this.address = 'Hai Duong';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onPhoneChange() {
    console.log(`Phone typed: ${this.phone}`);
  }

  register() {
    const message = `phone: ${this.phone}` +
                    `password: ${this.password}` +
                    `retypePassword: ${this.retypePassword}` +
                    `address: ${this.address}` +
                    `fullName: ${this.fullName}` +
                    `isAccepted: ${this.isAccepted}`;
    // alert(message);
    const apiUrl = "http://localhost:8080/api/v1/users/register"
    const registerData = {
      "fullname": this.fullName,
      "phone_number" : this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
    }
    const headers =  new HttpHeaders({ 'Content-Type': 'application/json'})
    this.http.post(apiUrl, registerData, {headers},)
    .subscribe({
      next: (response: any) => {
        debugger
        alert("Đăng ký thành công")
        this.router.navigate(['/login'])
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
        //Xử lý lỗi nếu có
        alert(`Cannot register, error: ${error.error}`)
      }
    })
  }

  checkPasswordsMatch(){
    if (this.password !== this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch': true});
    }else{
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }

  checkAge(){
    if (this.dateOfBirth){
      const today = new Date()
      const birthDate = new Date(this.dateOfBirth)
      let age = today.getFullYear() - birthDate.getFullYear()
      const monthDiff = today.getMonth() - birthDate.getMonth()
      if(monthDiff < 0 || (monthDiff == 0 && today.getDate() < birthDate.getDate())){
        age--;
      }
      if (age < 18){
        this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidAge' : true})
      }else{
        this.registerForm.form.controls['dateOfBirth'].setErrors(null)
      }
    }
  }
}