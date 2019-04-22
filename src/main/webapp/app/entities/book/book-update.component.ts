import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { ILibrary } from 'app/shared/model/library.model';
import { LibraryService } from 'app/entities/library';
import { IEditor } from 'app/shared/model/editor.model';
import { EditorService } from 'app/entities/editor';

@Component({
    selector: 'jhi-book-update',
    templateUrl: './book-update.component.html'
})
export class BookUpdateComponent implements OnInit {
    book: IBook;
    isSaving: boolean;

    authors: IAuthor[];

    categories: ICategory[];

    libraries: ILibrary[];

    editors: IEditor[];
    publicationDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bookService: BookService,
        protected authorService: AuthorService,
        protected categoryService: CategoryService,
        protected libraryService: LibraryService,
        protected editorService: EditorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ book }) => {
            this.book = book;
        });
        this.authorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAuthor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAuthor[]>) => response.body)
            )
            .subscribe((res: IAuthor[]) => (this.authors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.categoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategory[]>) => response.body)
            )
            .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.libraryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILibrary[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILibrary[]>) => response.body)
            )
            .subscribe((res: ILibrary[]) => (this.libraries = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.editorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEditor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEditor[]>) => response.body)
            )
            .subscribe((res: IEditor[]) => (this.editors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.book.id !== undefined) {
            this.subscribeToSaveResponse(this.bookService.update(this.book));
        } else {
            this.subscribeToSaveResponse(this.bookService.create(this.book));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>) {
        result.subscribe((res: HttpResponse<IBook>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAuthorById(index: number, item: IAuthor) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackLibraryById(index: number, item: ILibrary) {
        return item.id;
    }

    trackEditorById(index: number, item: IEditor) {
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
