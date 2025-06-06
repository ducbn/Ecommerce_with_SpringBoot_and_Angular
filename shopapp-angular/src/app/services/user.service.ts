import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { HttpUtilService } from './http.util.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiRegister = `${environment.apiUrl}/users/register`;
  private apiLogin = `${environment.apiUrl}/users/login`;
  private apiConfig: { headers: HttpHeaders };

  constructor(
    private http: HttpClient, 
    private httpUtilService: HttpUtilService
  ) {
    this.apiConfig = {
      headers: this.httpUtilService.createHeaders(),
    };
  }

  register(registerDTO: RegisterDTO): Observable<any> {
    return this.http.post(this.apiRegister, registerDTO, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.apiLogin, loginDTO, this.apiConfig);
  }
}
