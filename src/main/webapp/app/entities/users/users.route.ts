import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Users } from 'app/shared/model/users.model';
import { UsersService } from './users.service';
import { UsersComponent } from './users.component';
import { UsersDetailComponent } from './users-detail.component';
import { UsersUpdateComponent } from './users-update.component';
import { UsersDeletePopupComponent } from './users-delete-dialog.component';
import { IUsers } from 'app/shared/model/users.model';

@Injectable({ providedIn: 'root' })
export class UsersResolve implements Resolve<IUsers> {
    constructor(private service: UsersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUsers> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Users>) => response.ok),
                map((users: HttpResponse<Users>) => users.body)
            );
        }
        return of(new Users());
    }
}

export const usersRoute: Routes = [
    {
        path: '',
        component: UsersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UsersDetailComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UsersUpdateComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UsersUpdateComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usersPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UsersDeletePopupComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.users.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
