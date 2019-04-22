import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEditor } from 'app/shared/model/editor.model';
import { EditorService } from './editor.service';
import { ICoordinates } from 'app/shared/model/coordinates.model';
import { CoordinatesService } from 'app/entities/coordinates';

@Component({
    selector: 'jhi-editor-update',
    templateUrl: './editor-update.component.html'
})
export class EditorUpdateComponent implements OnInit {
    editor: IEditor;
    isSaving: boolean;

    coordinates: ICoordinates[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected editorService: EditorService,
        protected coordinatesService: CoordinatesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ editor }) => {
            this.editor = editor;
        });
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
        if (this.editor.id !== undefined) {
            this.subscribeToSaveResponse(this.editorService.update(this.editor));
        } else {
            this.subscribeToSaveResponse(this.editorService.create(this.editor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEditor>>) {
        result.subscribe((res: HttpResponse<IEditor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCoordinatesById(index: number, item: ICoordinates) {
        return item.id;
    }
}
