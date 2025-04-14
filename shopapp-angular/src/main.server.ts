import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { config } from './app/app.config.server';
import { HomeComponent } from './app/components/home/home.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';

const bootstrap = () => bootstrapApplication(LoginComponent, config);

export default bootstrap;
