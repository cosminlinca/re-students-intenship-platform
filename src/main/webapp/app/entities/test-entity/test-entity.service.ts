import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITestEntity } from 'app/shared/model/test-entity.model';

type EntityResponseType = HttpResponse<ITestEntity>;
type EntityArrayResponseType = HttpResponse<ITestEntity[]>;

@Injectable({ providedIn: 'root' })
export class TestEntityService {
  public resourceUrl = SERVER_API_URL + 'api/test-entities';

  constructor(protected http: HttpClient) {}

  create(testEntity: ITestEntity): Observable<EntityResponseType> {
    return this.http.post<ITestEntity>(this.resourceUrl, testEntity, { observe: 'response' });
  }

  update(testEntity: ITestEntity): Observable<EntityResponseType> {
    return this.http.put<ITestEntity>(this.resourceUrl, testEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITestEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITestEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
