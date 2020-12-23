import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';

@Component({
  templateUrl: './test-entity-delete-dialog.component.html',
})
export class TestEntityDeleteDialogComponent {
  testEntity?: ITestEntity;

  constructor(
    protected testEntityService: TestEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.testEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('testEntityListModification');
      this.activeModal.close();
    });
  }
}
