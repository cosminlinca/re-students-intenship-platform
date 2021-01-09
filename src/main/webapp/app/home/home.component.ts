import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer/offer.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { FormBuilder, FormGroup } from '@angular/forms';
@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  id = 0;
  account: Account | null = null;
  company: ICompany | null = null;
  offersStud?: IOffer[];
  offersCompany?: IOffer[];
  authSubscription?: Subscription;
  paidSelected = 'Not_Mentioned';
  domainSelected = '';
  domains: any;
  positionFilter: FormGroup;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    protected offerService: OfferService,
    protected companyService: CompanyService,
    protected router: Router,
    private formBuilder: FormBuilder
  ) {
    this.positionFilter = this.formBuilder.group({
      positionTextFilter: [null, []],
    });
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      this.id = this.account == null ? 0 : this.account.id;
      if (this.isCompany()) this.getCompanyLoggedIn();

      if (this.isStudent()) this.getListOfOffersStudent();
    });
  }

  getListOfOffersStudent(): void {
    this.offerService.query({}).subscribe(
      (res: HttpResponse<IOffer[]>) => {
        this.offersStud = res.body || [];
        this.domains = this.offersStud.filter(value => value.domain !== '').map(offer => offer.domain);
      },
      () => {
        this.offersStud = [];
      }
    );
  }

  getListOfOffersCompany(): void {
    this.offerService.query({}).subscribe(
      (res: HttpResponse<IOffer[]>) => {
        this.offersCompany = res.body || [];
        if (this.company != null) {
          const companyId = this.company.id;
          this.offersCompany = this.offersCompany.filter(value => value.companyId === companyId);
        } else {
          this.offersCompany = [];
        }
      },
      () => {
        this.offersCompany = [];
      }
    );
  }

  getCompanyLoggedIn(): void {
    if (this.isCompany()) {
      this.companyService.findByUserId(Number(this.id)).subscribe(value => {
        this.company = value.body;
        this.getListOfOffersCompany();
      });
    }
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

  onFilterOffers(): void {
    this.offerService.query({}).subscribe(
      (res: HttpResponse<IOffer[]>) => {
        this.offersStud = res.body || [];

        let selected: boolean;
        switch (this.paidSelected) {
          case 'Paid': {
            selected = true;
            break;
          }
          case 'Unpaid': {
            selected = false;
            break;
          }
        }
        if (this.paidSelected !== '') this.offersStud = this.offersStud.filter(value => value.paid === selected);
        if (this.domainSelected !== '') this.offersStud = this.offersStud.filter(value => value.domain === this.domainSelected);
        const positionFormValue = this.positionFilter.controls['positionTextFilter'].value.toString().toLowerCase();
        if (positionFormValue !== '')
          this.offersStud = this.offersStud.filter(value => value.positionName?.toLowerCase().includes(positionFormValue));
      },
      () => {
        this.offersStud = [];
      }
    );
  }
}
