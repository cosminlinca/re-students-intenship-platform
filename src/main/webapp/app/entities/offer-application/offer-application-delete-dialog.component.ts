import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferApplication } from 'app/shared/model/offer-application.model';
import { OfferApplicationService } from './offer-application.service';

@Component({
  templateUrl: './offer-application-delete-dialog.component.html',
})
export class OfferApplicationDeleteDialogComponent {
  offerApplication?: IOfferApplication;

  constructor(
    protected offerApplicationService: OfferApplicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offerApplicationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('offerApplicationListModification');
      this.activeModal.close();
    });
  }
}
