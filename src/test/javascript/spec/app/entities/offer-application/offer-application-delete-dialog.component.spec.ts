import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { StudentsIntenshipPlatformAvraTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { OfferApplicationDeleteDialogComponent } from 'app/entities/offer-application/offer-application-delete-dialog.component';
import { OfferApplicationService } from 'app/entities/offer-application/offer-application.service';

describe('Component Tests', () => {
  describe('OfferApplication Management Delete Component', () => {
    let comp: OfferApplicationDeleteDialogComponent;
    let fixture: ComponentFixture<OfferApplicationDeleteDialogComponent>;
    let service: OfferApplicationService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StudentsIntenshipPlatformAvraTestModule],
        declarations: [OfferApplicationDeleteDialogComponent],
      })
        .overrideTemplate(OfferApplicationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferApplicationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferApplicationService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
