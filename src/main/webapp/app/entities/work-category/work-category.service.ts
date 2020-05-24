import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkCategory } from 'app/shared/model/work-category.model';

type EntityResponseType = HttpResponse<IWorkCategory>;
type EntityArrayResponseType = HttpResponse<IWorkCategory[]>;

@Injectable({ providedIn: 'root' })
export class WorkCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/work-categories';

  constructor(protected http: HttpClient) {}

  create(workCategory: IWorkCategory): Observable<EntityResponseType> {
    return this.http.post<IWorkCategory>(this.resourceUrl, workCategory, { observe: 'response' });
  }

  update(workCategory: IWorkCategory): Observable<EntityResponseType> {
    return this.http.put<IWorkCategory>(this.resourceUrl, workCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
