import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StudentsIntenshipPlatformAvraSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [StudentsIntenshipPlatformAvraSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class StudentsIntenshipPlatformAvraHomeModule {}
