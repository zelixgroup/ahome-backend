import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIdDocument } from 'app/shared/model/id-document.model';

type EntityResponseType = HttpResponse<IIdDocument>;
type EntityArrayResponseType = HttpResponse<IIdDocument[]>;

@Injectable({ providedIn: 'root' })
export class IdDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/id-documents';

  constructor(protected http: HttpClient) {}

  create(idDocument: IIdDocument): Observable<EntityResponseType> {
    return this.http.post<IIdDocument>(this.resourceUrl, idDocument, { observe: 'response' });
  }

  update(idDocument: IIdDocument): Observable<EntityResponseType> {
    return this.http.put<IIdDocument>(this.resourceUrl, idDocument, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIdDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIdDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
