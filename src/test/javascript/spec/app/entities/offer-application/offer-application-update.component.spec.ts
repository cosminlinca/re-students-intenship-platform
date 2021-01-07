import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StudentsIntenshipPlatformAvraTestModule } from '../../../test.module';
import { OfferApplicationUpdateComponent } from 'app/entities/offer-application/offer-application-update.component';
import { OfferApplicationService } from 'app/entities/offer-application/offer-application.service';
import { OfferApplication } from 'app/shared/model/offer-application.model';

describe('Component Tests', () => {
  describe('OfferApplication Management Update Component', () => {
    let comp: OfferApplicationUpdateComponent;
    let fixture: ComponentFixture<OfferApplicationUpdateComponent>;
    let service: OfferApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StudentsIntenshipPlatformAvraTestModule],
        declarations: [OfferApplicationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OfferApplicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferApplicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferApplicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OfferApplication(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OfferApplication();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
