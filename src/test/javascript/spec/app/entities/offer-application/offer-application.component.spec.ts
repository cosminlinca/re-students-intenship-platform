import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { StudentsIntenshipPlatformAvraTestModule } from '../../../test.module';
import { OfferApplicationComponent } from 'app/entities/offer-application/offer-application.component';
import { OfferApplicationService } from 'app/entities/offer-application/offer-application.service';
import { OfferApplication } from 'app/shared/model/offer-application.model';

describe('Component Tests', () => {
  describe('OfferApplication Management Component', () => {
    let comp: OfferApplicationComponent;
    let fixture: ComponentFixture<OfferApplicationComponent>;
    let service: OfferApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StudentsIntenshipPlatformAvraTestModule],
        declarations: [OfferApplicationComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(OfferApplicationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferApplicationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferApplicationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OfferApplication(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.offerApplications && comp.offerApplications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OfferApplication(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.offerApplications && comp.offerApplications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
