import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICoordinates } from 'app/shared/model/coordinates.model';
import { CoordinatesService } from './coordinates.service';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { ILibrary } from 'app/shared/model/library.model';
import { LibraryService } from 'app/entities/library';
import { IEditor } from 'app/shared/model/editor.model';
import { EditorService } from 'app/entities/editor';

@Component({
    selector: 'jhi-coordinates-update',
    templateUrl: './coordinates-update.component.html'
})
export class CoordinatesUpdateComponent implements OnInit {
    coordinates: ICoordinates;
    isSaving: boolean;

    users: IUsers[];

    libraries: ILibrary[];

    editors: IEditor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected coordinatesService: CoordinatesService,
        protected usersService: UsersService,
        protected libraryService: LibraryService,
        protected editorService: EditorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ coordinates }) => {
            this.coordinates = coordinates;
        });
        this.usersService
            .query({ filter: 'coordinates-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe(
                (res: IUsers[]) => {
                    if (!this.coordinates.users || !this.coordinates.users.id) {
                        this.users = res;
                    } else {
                        this.usersService
                            .find(this.coordinates.users.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUsers>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUsers>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUsers) => (this.users = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.libraryService
            .query({ filter: 'coordinates-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILibrary[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILibrary[]>) => response.body)
            )
            .subscribe(
                (res: ILibrary[]) => {
                    if (!this.coordinates.library || !this.coordinates.library.id) {
                        this.libraries = res;
                    } else {
                        this.libraryService
                            .find(this.coordinates.library.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ILibrary>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ILibrary>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ILibrary) => (this.libraries = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.editorService
            .query({ filter: 'coordinates-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IEditor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEditor[]>) => response.body)
            )
            .subscribe(
                (res: IEditor[]) => {
                    if (!this.coordinates.editor || !this.coordinates.editor.id) {
                        this.editors = res;
                    } else {
                        this.editorService
                            .find(this.coordinates.editor.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IEditor>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IEditor>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IEditor) => (this.editors = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.coordinates.id !== undefined) {
            this.subscribeToSaveResponse(this.coordinatesService.update(this.coordinates));
        } else {
            this.subscribeToSaveResponse(this.coordinatesService.create(this.coordinates));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoordinates>>) {
        result.subscribe((res: HttpResponse<ICoordinates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackLibraryById(index: number, item: ILibrary) {
        return item.id;
    }

    trackEditorById(index: number, item: IEditor) {
        return item.id;
    }
}
