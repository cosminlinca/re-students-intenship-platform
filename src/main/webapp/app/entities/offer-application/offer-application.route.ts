import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOfferApplication, OfferApplication } from 'app/shared/model/offer-application.model';
import { OfferApplicationService } from './offer-application.service';
import { OfferApplicationComponent } from './offer-application.component';
import { OfferApplicationDetailComponent } from './offer-application-detail.component';
import { OfferApplicationUpdateComponent } from './offer-application-update.component';

@Injectable({ providedIn: 'root' })
export class OfferApplicationResolve implements Resolve<IOfferApplication> {
  constructor(private service: OfferApplicationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOfferApplication> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((offerApplication: HttpResponse<OfferApplication>) => {
          if (offerApplication.body) {
            return of(offerApplication.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OfferApplication());
  }
}

export const offerApplicationRoute: Routes = [
  {
    path: '',
    component: OfferApplicationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'studentsIntenshipPlatformAvraApp.offerApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OfferApplicationDetailComponent,
    resolve: {
      offerApplication: OfferApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'studentsIntenshipPlatformAvraApp.offerApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OfferApplicationUpdateComponent,
    resolve: {
      offerApplication: OfferApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'studentsIntenshipPlatformAvraApp.offerApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OfferApplicationUpdateComponent,
    resolve: {
      offerApplication: OfferApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'studentsIntenshipPlatformAvraApp.offerApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
