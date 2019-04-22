import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILibrary } from 'app/shared/model/library.model';

type EntityResponseType = HttpResponse<ILibrary>;
type EntityArrayResponseType = HttpResponse<ILibrary[]>;

@Injectable({ providedIn: 'root' })
export class LibraryService {
    public resourceUrl = SERVER_API_URL + 'api/libraries';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/libraries';

    constructor(protected http: HttpClient) {}

    create(library: ILibrary): Observable<EntityResponseType> {
        return this.http.post<ILibrary>(this.resourceUrl, library, { observe: 'response' });
    }

    update(library: ILibrary): Observable<EntityResponseType> {
        return this.http.put<ILibrary>(this.resourceUrl, library, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILibrary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILibrary[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILibrary[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
