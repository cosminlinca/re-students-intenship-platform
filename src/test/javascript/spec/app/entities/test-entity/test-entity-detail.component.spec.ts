import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StudentsIntenshipPlatformAvraTestModule } from '../../../test.module';
import { TestEntityDetailComponent } from 'app/entities/test-entity/test-entity-detail.component';
import { TestEntity } from 'app/shared/model/test-entity.model';

describe('Component Tests', () => {
  describe('TestEntity Management Detail Component', () => {
    let comp: TestEntityDetailComponent;
    let fixture: ComponentFixture<TestEntityDetailComponent>;
    const route = ({ data: of({ testEntity: new TestEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StudentsIntenshipPlatformAvraTestModule],
        declarations: [TestEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TestEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load testEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
