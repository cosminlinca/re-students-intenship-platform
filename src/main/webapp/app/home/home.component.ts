import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer/offer.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  offers?: IOffer[];
  authSubscription?: Subscription;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    protected offerService: OfferService,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.getListOfOffers();
  }

  getListOfOffers(): void {
    this.offerService.query({}).subscribe(
      (res: HttpResponse<IOffer[]>) => {
        this.offers = res.body || [];
      },
      () => {
        this.offers = [];
      }
    );
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  isAdmin(): boolean {
    return this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
