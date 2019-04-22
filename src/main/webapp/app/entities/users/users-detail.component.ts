import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsers } from 'app/shared/model/users.model';

@Component({
    selector: 'jhi-users-detail',
    templateUrl: './users-detail.component.html'
})
export class UsersDetailComponent implements OnInit {
    users: IUsers;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ users }) => {
            this.users = users;
        });
    }

    previousState() {
        window.history.back();
    }
}
