import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOfferApplication } from 'app/shared/model/offer-application.model';

@Component({
  selector: 'jhi-offer-application-detail',
  templateUrl: './offer-application-detail.component.html',
})
export class OfferApplicationDetailComponent implements OnInit {
  offerApplication: IOfferApplication | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerApplication }) => (this.offerApplication = offerApplication));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
