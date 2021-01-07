import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StudentsIntenshipPlatformAvraSharedModule } from 'app/shared/shared.module';
import { CompanyComponent } from './company.component';
import { CompanyDetailComponent } from './company-detail.component';
import { CompanyUpdateComponent } from './company-update.component';
import { CompanyDeleteDialogComponent } from './company-delete-dialog.component';
import { companyRoute } from './company.route';
import {AccountModule} from "../../account/account.module";

@NgModule({
  imports: [StudentsIntenshipPlatformAvraSharedModule, RouterModule.forChild(companyRoute), AccountModule],
  declarations: [CompanyComponent, CompanyDetailComponent, CompanyUpdateComponent, CompanyDeleteDialogComponent],
  entryComponents: [CompanyDeleteDialogComponent],
})
export class StudentsIntenshipPlatformAvraCompanyModule {}
