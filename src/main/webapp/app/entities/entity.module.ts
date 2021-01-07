import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'test-entity',
        loadChildren: () => import('./test-entity/test-entity.module').then(m => m.StudentsIntenshipPlatformAvraTestEntityModule),
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.StudentsIntenshipPlatformAvraStudentModule),
      },
      {
        path: 'offer',
        loadChildren: () => import('./offer/offer.module').then(m => m.StudentsIntenshipPlatformAvraOfferModule),
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.StudentsIntenshipPlatformAvraCompanyModule),
      },
      {
        path: 'offer-application',
        loadChildren: () =>
          import('./offer-application/offer-application.module').then(m => m.StudentsIntenshipPlatformAvraOfferApplicationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class StudentsIntenshipPlatformAvraEntityModule {}
