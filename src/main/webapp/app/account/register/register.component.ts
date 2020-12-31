import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {FormBuilder, Validators} from '@angular/forms';
import {JhiLanguageService, JhiDataUtils, JhiEventWithContent, JhiFileLoadError, JhiEventManager} from 'ng-jhipster';

import {EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE} from 'app/shared/constants/error.constants';
import {LoginModalService} from 'app/core/login/login-modal.service';
import {RegisterService} from './register.service';
import {AlertError} from "../../shared/alert/alert-error.model";

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('login', {static: false})
  login?: ElementRef;

  doNotMatch = false;
  error = false;
  errorEmailExists = false;
  errorUserExists = false;
  success = false;

  registerForm = this.fb.group({
    login: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    ],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    id: [''],
    university: [''],
    faculty: [''],
    profile: [''],
    yearOfStudy: [0],
    observations: [''],
    cvDocument: [],
    cvDocumentContentType: [],
    user: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    private languageService: JhiLanguageService,
    private loginModalService: LoginModalService,
    private registerService: RegisterService,
    private fb: FormBuilder
  ) {
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.registerForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('studentsIntenshipPlatformAvraApp.error', {
          ...err,
          key: 'error.file.' + err.key
        })
      );
    });
  }

  ngAfterViewInit(): void {
    if (this.login) {
      this.login.nativeElement.focus();
    }
  }

  register(): void {
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;

    const password = this.registerForm.get(['password'])!.value;
    if (password !== this.registerForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    } else {
      const login = this.registerForm.get(['login'])!.value;
      const email = this.registerForm.get(['email'])!.value;
      const firstName = this.registerForm.get(['firstName'])!.value;
      const lastName = this.registerForm.get(['lastName'])!.value;

      const university = this.registerForm.get(['university'])!.value;
      const faculty = this.registerForm.get(['faculty'])!.value;
      const profile = this.registerForm.get(['profile'])!.value;
      const yearOfStudy = this.registerForm.get(['yearOfStudy'])!.value;
      const observations = this.registerForm.get(['observations'])!.value;
      const cvDocumentContentType = this.registerForm.get(['cvDocumentContentType'])!.value;
      const cvDocument = this.registerForm.get(['cvDocument'])!.value;

      this.registerService.save({login, email, firstName, lastName, password, langKey: this.languageService.getCurrentLanguage(),
        university, faculty, profile, yearOfStudy, observations, cvDocument, cvDocumentContentType}).subscribe(
        () => (this.success = true),
        response => this.processError(response)
      );
    }
  }

  openLogin(): void {
    this.loginModalService.open();
  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }
}
