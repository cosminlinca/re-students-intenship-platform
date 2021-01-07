import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IOfferApplication, OfferApplication } from 'app/shared/model/offer-application.model';
import { OfferApplicationService } from './offer-application.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-offer-application-update',
  templateUrl: './offer-application-update.component.html',
})
export class OfferApplicationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idStudent: [],
    idOffer: [],
    attachedCV: [],
    attachedCVContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected offerApplicationService: OfferApplicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerApplication }) => {
      this.updateForm(offerApplication);
    });
  }

  updateForm(offerApplication: IOfferApplication): void {
    this.editForm.patchValue({
      id: offerApplication.id,
      idStudent: offerApplication.idStudent,
      idOffer: offerApplication.idOffer,
      attachedCV: offerApplication.attachedCV,
      attachedCVContentType: offerApplication.attachedCVContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('studentsIntenshipPlatformAvraApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offerApplication = this.createFromForm();
    if (offerApplication.id !== undefined) {
      this.subscribeToSaveResponse(this.offerApplicationService.update(offerApplication));
    } else {
      this.subscribeToSaveResponse(this.offerApplicationService.create(offerApplication));
    }
  }

  private createFromForm(): IOfferApplication {
    return {
      ...new OfferApplication(),
      id: this.editForm.get(['id'])!.value,
      idStudent: this.editForm.get(['idStudent'])!.value,
      idOffer: this.editForm.get(['idOffer'])!.value,
      attachedCVContentType: this.editForm.get(['attachedCVContentType'])!.value,
      attachedCV: this.editForm.get(['attachedCV'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfferApplication>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
