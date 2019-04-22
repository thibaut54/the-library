import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBook } from 'app/shared/model/book.model';
import { AccountService } from 'app/core';
import { BookService } from './book.service';

@Component({
    selector: 'jhi-book',
    templateUrl: './book.component.html'
})
export class BookComponent implements OnInit, OnDestroy {
    books: IBook[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected bookService: BookService,
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
            this.bookService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IBook[]>) => res.ok),
                    map((res: HttpResponse<IBook[]>) => res.body)
                )
                .subscribe((res: IBook[]) => (this.books = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.bookService
            .query()
            .pipe(
                filter((res: HttpResponse<IBook[]>) => res.ok),
                map((res: HttpResponse<IBook[]>) => res.body)
            )
            .subscribe(
                (res: IBook[]) => {
                    this.books = res;
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
        this.registerChangeInBooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBook) {
        return item.id;
    }

    registerChangeInBooks() {
        this.eventSubscriber = this.eventManager.subscribe('bookListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
