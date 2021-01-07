import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StudentsIntenshipPlatformAvraSharedModule } from 'app/shared/shared.module';
import { OfferApplicationComponent } from './offer-application.component';
import { OfferApplicationDetailComponent } from './offer-application-detail.component';
import { OfferApplicationUpdateComponent } from './offer-application-update.component';
import { OfferApplicationDeleteDialogComponent } from './offer-application-delete-dialog.component';
import { offerApplicationRoute } from './offer-application.route';

@NgModule({
  imports: [StudentsIntenshipPlatformAvraSharedModule, RouterModule.forChild(offerApplicationRoute)],
  declarations: [
    OfferApplicationComponent,
    OfferApplicationDetailComponent,
    OfferApplicationUpdateComponent,
    OfferApplicationDeleteDialogComponent,
  ],
  entryComponents: [OfferApplicationDeleteDialogComponent],
})
export class StudentsIntenshipPlatformAvraOfferApplicationModule {}
