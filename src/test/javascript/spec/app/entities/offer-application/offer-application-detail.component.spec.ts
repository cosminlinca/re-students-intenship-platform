import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { StudentsIntenshipPlatformAvraTestModule } from '../../../test.module';
import { OfferApplicationDetailComponent } from 'app/entities/offer-application/offer-application-detail.component';
import { OfferApplication } from 'app/shared/model/offer-application.model';

describe('Component Tests', () => {
  describe('OfferApplication Management Detail Component', () => {
    let comp: OfferApplicationDetailComponent;
    let fixture: ComponentFixture<OfferApplicationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ offerApplication: new OfferApplication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StudentsIntenshipPlatformAvraTestModule],
        declarations: [OfferApplicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OfferApplicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferApplicationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load offerApplication on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerApplication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
