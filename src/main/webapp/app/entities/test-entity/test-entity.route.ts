import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITestEntity, TestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';
import { TestEntityComponent } from './test-entity.component';
import { TestEntityDetailComponent } from './test-entity-detail.component';
import { TestEntityUpdateComponent } from './test-entity-update.component';

@Injectable({ providedIn: 'root' })
export class TestEntityResolve implements Resolve<ITestEntity> {
  constructor(private service: TestEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITestEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((testEntity: HttpResponse<TestEntity>) => {
          if (testEntity.body) {
            return of(testEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TestEntity());
  }
}

export const testEntityRoute: Routes = [
  {
    path: '',
    component: TestEntityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'studentsIntenshipPlatformAvraApp.testEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TestEntityDetailComponent,
    resolve: {
      testEntity: TestEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'studentsIntenshipPlatformAvraApp.testEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TestEntityUpdateComponent,
    resolve: {
      testEntity: TestEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'studentsIntenshipPlatformAvraApp.testEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TestEntityUpdateComponent,
    resolve: {
      testEntity: TestEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'studentsIntenshipPlatformAvraApp.testEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
