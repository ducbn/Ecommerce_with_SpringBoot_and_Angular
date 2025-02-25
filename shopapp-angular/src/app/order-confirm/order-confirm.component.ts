import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-order-confirm',
  imports: [FooterComponent, HeaderComponent],
  templateUrl: './order-confirm.component.html',
  styleUrl: './order-confirm.component.css'
})
export class OrderConfirmComponent {

}
