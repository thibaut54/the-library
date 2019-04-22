import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICoordinates } from 'app/shared/model/coordinates.model';

type EntityResponseType = HttpResponse<ICoordinates>;
type EntityArrayResponseType = HttpResponse<ICoordinates[]>;

@Injectable({ providedIn: 'root' })
export class CoordinatesService {
    public resourceUrl = SERVER_API_URL + 'api/coordinates';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/coordinates';

    constructor(protected http: HttpClient) {}

    create(coordinates: ICoordinates): Observable<EntityResponseType> {
        return this.http.post<ICoordinates>(this.resourceUrl, coordinates, { observe: 'response' });
    }

    update(coordinates: ICoordinates): Observable<EntityResponseType> {
        return this.http.put<ICoordinates>(this.resourceUrl, coordinates, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICoordinates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICoordinates[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICoordinates[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
