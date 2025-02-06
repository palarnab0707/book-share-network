import { Component, OnInit } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerRequest: RegistrationRequest = {email: "", firstName: "", lastName: "", password: ""};
  errorMessage: Array<string> = [];

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit(): void {
  }

  register() {
    this.errorMessage = [];
    this.authenticationService.register({
      body: this.registerRequest
    }).subscribe({
      next: (result) => {
        this.router.navigate(['authenticate-account']);
      },
      error: (error) => {
        this.errorMessage = error.error.validationErrors;
      }
    })
  }

  login() {
    this.router.navigate(['login']);
  }
}
