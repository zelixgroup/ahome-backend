import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIdDocumentType } from 'app/shared/model/id-document-type.model';

type EntityResponseType = HttpResponse<IIdDocumentType>;
type EntityArrayResponseType = HttpResponse<IIdDocumentType[]>;

@Injectable({ providedIn: 'root' })
export class IdDocumentTypeService {
  public resourceUrl = SERVER_API_URL + 'api/id-document-types';

  constructor(protected http: HttpClient) {}

  create(idDocumentType: IIdDocumentType): Observable<EntityResponseType> {
    return this.http.post<IIdDocumentType>(this.resourceUrl, idDocumentType, { observe: 'response' });
  }

  update(idDocumentType: IIdDocumentType): Observable<EntityResponseType> {
    return this.http.put<IIdDocumentType>(this.resourceUrl, idDocumentType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIdDocumentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIdDocumentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
