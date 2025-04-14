import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-order',
  imports: [FooterComponent, HeaderComponent],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent {

}
