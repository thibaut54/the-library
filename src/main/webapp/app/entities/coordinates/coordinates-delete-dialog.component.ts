import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoordinates } from 'app/shared/model/coordinates.model';
import { CoordinatesService } from './coordinates.service';

@Component({
    selector: 'jhi-coordinates-delete-dialog',
    templateUrl: './coordinates-delete-dialog.component.html'
})
export class CoordinatesDeleteDialogComponent {
    coordinates: ICoordinates;

    constructor(
        protected coordinatesService: CoordinatesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coordinatesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'coordinatesListModification',
                content: 'Deleted an coordinates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coordinates-delete-popup',
    template: ''
})
export class CoordinatesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ coordinates }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CoordinatesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.coordinates = coordinates;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/coordinates', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/coordinates', { outlets: { popup: null } }]);
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
