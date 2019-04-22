import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEditor } from 'app/shared/model/editor.model';

type EntityResponseType = HttpResponse<IEditor>;
type EntityArrayResponseType = HttpResponse<IEditor[]>;

@Injectable({ providedIn: 'root' })
export class EditorService {
    public resourceUrl = SERVER_API_URL + 'api/editors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/editors';

    constructor(protected http: HttpClient) {}

    create(editor: IEditor): Observable<EntityResponseType> {
        return this.http.post<IEditor>(this.resourceUrl, editor, { observe: 'response' });
    }

    update(editor: IEditor): Observable<EntityResponseType> {
        return this.http.put<IEditor>(this.resourceUrl, editor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEditor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEditor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEditor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
