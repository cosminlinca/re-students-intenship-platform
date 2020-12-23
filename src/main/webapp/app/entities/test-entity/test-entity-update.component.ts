import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITestEntity, TestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';

@Component({
  selector: 'jhi-test-entity-update',
  templateUrl: './test-entity-update.component.html',
})
export class TestEntityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    year: [],
  });

  constructor(protected testEntityService: TestEntityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testEntity }) => {
      this.updateForm(testEntity);
    });
  }

  updateForm(testEntity: ITestEntity): void {
    this.editForm.patchValue({
      id: testEntity.id,
      name: testEntity.name,
      year: testEntity.year,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const testEntity = this.createFromForm();
    if (testEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.testEntityService.update(testEntity));
    } else {
      this.subscribeToSaveResponse(this.testEntityService.create(testEntity));
    }
  }

  private createFromForm(): ITestEntity {
    return {
      ...new TestEntity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      year: this.editForm.get(['year'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestEntity>>): void {
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
