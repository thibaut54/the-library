import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users-delete-dialog',
    templateUrl: './users-delete-dialog.component.html'
})
export class UsersDeleteDialogComponent {
    users: IUsers;

    constructor(protected usersService: UsersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.usersService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'usersListModification',
                content: 'Deleted an users'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-users-delete-popup',
    template: ''
})
export class UsersDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ users }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UsersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.users = users;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/users', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/users', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
