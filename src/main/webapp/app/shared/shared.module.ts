import { NgModule } from '@angular/core';
import { StudentsIntenshipPlatformAvraSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

import {JhMaterialModule} from 'app/shared/jh-material.module';
@NgModule({
  imports: [JhMaterialModule, StudentsIntenshipPlatformAvraSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent],
  exports: [JhMaterialModule, 
    StudentsIntenshipPlatformAvraSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
  ],
})
export class StudentsIntenshipPlatformAvraSharedModule {}
