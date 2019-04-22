import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUsers } from 'app/shared/model/users.model';
import { AccountService } from 'app/core';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users',
    templateUrl: './users.component.html'
})
export class UsersComponent implements OnInit, OnDestroy {
    users: IUsers[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected usersService: UsersService,
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
            this.usersService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IUsers[]>) => res.ok),
                    map((res: HttpResponse<IUsers[]>) => res.body)
                )
                .subscribe((res: IUsers[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.usersService
            .query()
            .pipe(
                filter((res: HttpResponse<IUsers[]>) => res.ok),
                map((res: HttpResponse<IUsers[]>) => res.body)
            )
            .subscribe(
                (res: IUsers[]) => {
                    this.users = res;
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
        this.registerChangeInUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUsers) {
        return item.id;
    }

    registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe('usersListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
