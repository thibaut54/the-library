import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUsers } from 'app/shared/model/users.model';

type EntityResponseType = HttpResponse<IUsers>;
type EntityArrayResponseType = HttpResponse<IUsers[]>;

@Injectable({ providedIn: 'root' })
export class UsersService {
    public resourceUrl = SERVER_API_URL + 'api/users';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/users';

    constructor(protected http: HttpClient) {}

    create(users: IUsers): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(users);
        return this.http
            .post<IUsers>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(users: IUsers): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(users);
        return this.http
            .put<IUsers>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUsers[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUsers[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(users: IUsers): IUsers {
        const copy: IUsers = Object.assign({}, users, {
            registrationDate:
                users.registrationDate != null && users.registrationDate.isValid() ? users.registrationDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.registrationDate = res.body.registrationDate != null ? moment(res.body.registrationDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((users: IUsers) => {
                users.registrationDate = users.registrationDate != null ? moment(users.registrationDate) : null;
            });
        }
        return res;
    }
}
