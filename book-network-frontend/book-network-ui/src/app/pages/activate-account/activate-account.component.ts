import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent implements OnInit {

  message:string = '';
  isOkay: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
  ) { }

  ngOnInit(): void {
  }

  onCodeCompleted(activationCode: string) {
    this.confirmAccount(activationCode);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(activationCode: string) {
    this.authenticationService.confirm({
      token : activationCode
    }).subscribe({
      next: result => {
        this.message = 'Your account has been successfully activated.\nNow You can proceed to log in.';
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid';
        this.submitted = true;
        this.isOkay = false;
      }
    })
  }
}
