import { Component, OnInit } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {register} from "../../services/fn/authentication/register";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMessage: Array<string> = [];

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private tokenService:TokenService
  ) { }

  ngOnInit(): void {
  }

  login() {
    this.errorMessage = [];
    this.authenticationService.authenticate(
      {body:this.authRequest}
    ).subscribe({
      next: (result) => {
        // todo save the token
        this.tokenService.token = result.token as string;
        this.router.navigate(['books']);
      },
      error: (error) => {
        console.log(error);
        if (error.error.validationErrors?.length > 0) {
          this.errorMessage = error.error.validationErrors;
        } else {
          this.errorMessage.push(error.error.businessErrorDescription);
        }

      }
    })
  }

  register() {
    this.router.navigate(['register']);
  }
}
