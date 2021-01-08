import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOffer, Offer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html',
})
export class OfferUpdateComponent implements OnInit {
  isSaving = false;
  offer: IOffer | null = null;

  editForm = this.fb.group({
    id: [],
    positionName: [],
    programDurationInWeeks: [],
    requiredSkills: [],
    technologies: [],
    details: [],
    paid: [],
    observations: [],
    domain: [],
    companyId: [],
    coverImagePath: [],
  });

  constructor(protected offerService: OfferService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.offer = offer;
      this.updateForm(offer);
    });
  }

  updateForm(offer: IOffer): void {
    this.editForm.patchValue({
      id: offer.id,
      positionName: offer.positionName,
      programDurationInWeeks: offer.programDurationInWeeks,
      requiredSkills: offer.requiredSkills,
      technologies: offer.technologies,
      details: offer.details,
      paid: offer.paid,
      observations: offer.observations,
      domain: offer.domain,
      companyId: offer.companyId,
      coverImagePath: offer.coverImagePath,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offer = this.createFromForm();
    if (offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  private createFromForm(): IOffer {
    return {
      ...new Offer(),
      id: this.editForm.get(['id'])!.value,
      positionName: this.editForm.get(['positionName'])!.value,
      programDurationInWeeks: this.editForm.get(['programDurationInWeeks'])!.value,
      requiredSkills: this.editForm.get(['requiredSkills'])!.value,
      technologies: this.editForm.get(['technologies'])!.value,
      details: this.editForm.get(['details'])!.value,
      paid: this.editForm.get(['paid'])!.value,
      observations: this.editForm.get(['observations'])!.value,
      domain: this.editForm.get(['domain'])!.value,
      companyId: this.editForm.get(['companyId'])!.value,
      coverImagePath: this.editForm.get(['coverImagePath'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>): void {
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
