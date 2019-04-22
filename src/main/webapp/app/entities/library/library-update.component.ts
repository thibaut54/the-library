import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILibrary } from 'app/shared/model/library.model';
import { LibraryService } from './library.service';
import { ICoordinates } from 'app/shared/model/coordinates.model';
import { CoordinatesService } from 'app/entities/coordinates';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book';

@Component({
    selector: 'jhi-library-update',
    templateUrl: './library-update.component.html'
})
export class LibraryUpdateComponent implements OnInit {
    library: ILibrary;
    isSaving: boolean;

    coordinates: ICoordinates[];

    books: IBook[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected libraryService: LibraryService,
        protected coordinatesService: CoordinatesService,
        protected bookService: BookService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ library }) => {
            this.library = library;
        });
        this.coordinatesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICoordinates[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICoordinates[]>) => response.body)
            )
            .subscribe((res: ICoordinates[]) => (this.coordinates = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bookService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBook[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBook[]>) => response.body)
            )
            .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.library.id !== undefined) {
            this.subscribeToSaveResponse(this.libraryService.update(this.library));
        } else {
            this.subscribeToSaveResponse(this.libraryService.create(this.library));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILibrary>>) {
        result.subscribe((res: HttpResponse<ILibrary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCoordinatesById(index: number, item: ICoordinates) {
        return item.id;
    }

    trackBookById(index: number, item: IBook) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
