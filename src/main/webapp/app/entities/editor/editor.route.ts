import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Editor } from 'app/shared/model/editor.model';
import { EditorService } from './editor.service';
import { EditorComponent } from './editor.component';
import { EditorDetailComponent } from './editor-detail.component';
import { EditorUpdateComponent } from './editor-update.component';
import { EditorDeletePopupComponent } from './editor-delete-dialog.component';
import { IEditor } from 'app/shared/model/editor.model';

@Injectable({ providedIn: 'root' })
export class EditorResolve implements Resolve<IEditor> {
    constructor(private service: EditorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEditor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Editor>) => response.ok),
                map((editor: HttpResponse<Editor>) => editor.body)
            );
        }
        return of(new Editor());
    }
}

export const editorRoute: Routes = [
    {
        path: '',
        component: EditorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.editor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EditorDetailComponent,
        resolve: {
            editor: EditorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.editor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EditorUpdateComponent,
        resolve: {
            editor: EditorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.editor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EditorUpdateComponent,
        resolve: {
            editor: EditorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.editor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const editorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EditorDeletePopupComponent,
        resolve: {
            editor: EditorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'theLibraryApp.editor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
