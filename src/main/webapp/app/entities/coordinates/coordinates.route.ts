import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Coordinates } from 'app/shared/model/coordinates.model';
import { CoordinatesService } from './coordinates.service';
import { CoordinatesComponent } from './coordinates.component';
import { CoordinatesDetailComponent } from './coordinates-detail.component';
import { CoordinatesUpdateComponent } from './coordinates-update.component';
import { CoordinatesDeletePopupComponent } from './coordinates-delete-dialog.component';
import { ICoordinates } from 'app/shared/model/coordinates.model';

@Injectable({ providedIn: 'root' })
export class CoordinatesResolve implements Resolve<ICoordinates> {
    constructor(private service: CoordinatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICoordinates> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Coordinates>) => response.ok),
                map((coordinates: HttpResponse<Coordinates>) => coordinates.body)
            );
        }
        return of(new Coordinates());
    }
}

export const coordinatesRoute: Routes = [
    {
        path: '',
        component: CoordinatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.coordinates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CoordinatesDetailComponent,
        resolve: {
            coordinates: CoordinatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.coordinates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CoordinatesUpdateComponent,
        resolve: {
            coordinates: CoordinatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.coordinates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CoordinatesUpdateComponent,
        resolve: {
            coordinates: CoordinatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.coordinates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coordinatesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CoordinatesDeletePopupComponent,
        resolve: {
            coordinates: CoordinatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.coordinates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
