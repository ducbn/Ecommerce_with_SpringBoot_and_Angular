import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, FooterComponent],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent {
  phone: string;
  password: string;
  retypePassword: string;
  fullName:string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;

  constructor(){
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address ='';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18)
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phone}`)
  }

  register(){
    const message = `phone: ${this.phone}` +
                    `password: ${this.password}`+
                    `retypePassword: ${this.retypePassword}`+
                    `address: ${this.address}`+
                    `fullName: ${this.fullName}`+
                    `isAccepted: ${this.isAccepted}`;
    alert(message)
  }
}