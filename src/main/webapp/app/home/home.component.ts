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
  offersStud?: IOffer[];
  offersCompany?: IOffer[];
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
        this.offersStud = res.body || [];
        this.offersCompany = this.offersStud;
        // TODO: filter offers created by current logged in company (when we will have the relationship with company table)
        // this.offersCompany = this.offersStud.filter(value => value.createdByCompany == this.account.firstName);
      },
      () => {
        this.offersStud = [];
        this.offersCompany = [];
      }
    );
  }

  showCompanyOffer(offer: IOffer): void {}

  openOfferDetails(offer: IOffer): void {}

  applyToOffer(offer: IOffer): void {}

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  isStudent(): boolean {
    return this.accountService.hasAnyAuthority(['ROLE_STUDENT']);
  }

  isCompany(): boolean {
    return this.accountService.hasAnyAuthority(['ROLE_COMPANY']);
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
