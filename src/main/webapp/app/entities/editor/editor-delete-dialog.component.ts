import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEditor } from 'app/shared/model/editor.model';
import { EditorService } from './editor.service';

@Component({
    selector: 'jhi-editor-delete-dialog',
    templateUrl: './editor-delete-dialog.component.html'
})
export class EditorDeleteDialogComponent {
    editor: IEditor;

    constructor(protected editorService: EditorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.editorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'editorListModification',
                content: 'Deleted an editor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-editor-delete-popup',
    template: ''
})
export class EditorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ editor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EditorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.editor = editor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/editor', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/editor', { outlets: { popup: null } }]);
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
