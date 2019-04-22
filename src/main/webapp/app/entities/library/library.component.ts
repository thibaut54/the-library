import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILibrary } from 'app/shared/model/library.model';
import { AccountService } from 'app/core';
import { LibraryService } from './library.service';

@Component({
    selector: 'jhi-library',
    templateUrl: './library.component.html'
})
export class LibraryComponent implements OnInit, OnDestroy {
    libraries: ILibrary[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected libraryService: LibraryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.libraryService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ILibrary[]>) => res.ok),
                    map((res: HttpResponse<ILibrary[]>) => res.body)
                )
                .subscribe((res: ILibrary[]) => (this.libraries = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.libraryService
            .query()
            .pipe(
                filter((res: HttpResponse<ILibrary[]>) => res.ok),
                map((res: HttpResponse<ILibrary[]>) => res.body)
            )
            .subscribe(
                (res: ILibrary[]) => {
                    this.libraries = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLibraries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILibrary) {
        return item.id;
    }

    registerChangeInLibraries() {
        this.eventSubscriber = this.eventManager.subscribe('libraryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
