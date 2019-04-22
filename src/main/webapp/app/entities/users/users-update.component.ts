import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from './users.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { ICoordinates } from 'app/shared/model/coordinates.model';
import { CoordinatesService } from 'app/entities/coordinates';

@Component({
    selector: 'jhi-users-update',
    templateUrl: './users-update.component.html'
})
export class UsersUpdateComponent implements OnInit {
    users: IUsers;
    isSaving: boolean;

    roles: IRole[];

    coordinates: ICoordinates[];
    registrationDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected usersService: UsersService,
        protected roleService: RoleService,
        protected coordinatesService: CoordinatesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ users }) => {
            this.users = users;
        });
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.coordinatesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICoordinates[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICoordinates[]>) => response.body)
            )
            .subscribe((res: ICoordinates[]) => (this.coordinates = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.users.id !== undefined) {
            this.subscribeToSaveResponse(this.usersService.update(this.users));
        } else {
            this.subscribeToSaveResponse(this.usersService.create(this.users));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsers>>) {
        result.subscribe((res: HttpResponse<IUsers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }

    trackCoordinatesById(index: number, item: ICoordinates) {
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
