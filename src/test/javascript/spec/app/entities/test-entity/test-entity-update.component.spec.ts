import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { StudentsIntenshipPlatformAvraTestModule } from '../../../test.module';
import { TestEntityUpdateComponent } from 'app/entities/test-entity/test-entity-update.component';
import { TestEntityService } from 'app/entities/test-entity/test-entity.service';
import { TestEntity } from 'app/shared/model/test-entity.model';

describe('Component Tests', () => {
  describe('TestEntity Management Update Component', () => {
    let comp: TestEntityUpdateComponent;
    let fixture: ComponentFixture<TestEntityUpdateComponent>;
    let service: TestEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [StudentsIntenshipPlatformAvraTestModule],
        declarations: [TestEntityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TestEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestEntity(123);
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
        const entity = new TestEntity();
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
