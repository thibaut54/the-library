import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICoordinates } from 'app/shared/model/coordinates.model';
import { AccountService } from 'app/core';
import { CoordinatesService } from './coordinates.service';

@Component({
    selector: 'jhi-coordinates',
    templateUrl: './coordinates.component.html'
})
export class CoordinatesComponent implements OnInit, OnDestroy {
    coordinates: ICoordinates[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected coordinatesService: CoordinatesService,
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
            this.coordinatesService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ICoordinates[]>) => res.ok),
                    map((res: HttpResponse<ICoordinates[]>) => res.body)
                )
                .subscribe((res: ICoordinates[]) => (this.coordinates = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.coordinatesService
            .query()
            .pipe(
                filter((res: HttpResponse<ICoordinates[]>) => res.ok),
                map((res: HttpResponse<ICoordinates[]>) => res.body)
            )
            .subscribe(
                (res: ICoordinates[]) => {
                    this.coordinates = res;
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
        this.registerChangeInCoordinates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICoordinates) {
        return item.id;
    }

    registerChangeInCoordinates() {
        this.eventSubscriber = this.eventManager.subscribe('coordinatesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
