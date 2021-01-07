import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOfferApplication } from 'app/shared/model/offer-application.model';

type EntityResponseType = HttpResponse<IOfferApplication>;
type EntityArrayResponseType = HttpResponse<IOfferApplication[]>;

@Injectable({ providedIn: 'root' })
export class OfferApplicationService {
  public resourceUrl = SERVER_API_URL + 'api/offer-applications';

  constructor(protected http: HttpClient) {}

  create(offerApplication: IOfferApplication): Observable<EntityResponseType> {
    return this.http.post<IOfferApplication>(this.resourceUrl, offerApplication, { observe: 'response' });
  }

  update(offerApplication: IOfferApplication): Observable<EntityResponseType> {
    return this.http.put<IOfferApplication>(this.resourceUrl, offerApplication, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOfferApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferApplication[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
