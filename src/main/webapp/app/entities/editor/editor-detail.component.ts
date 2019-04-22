import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEditor } from 'app/shared/model/editor.model';

@Component({
    selector: 'jhi-editor-detail',
    templateUrl: './editor-detail.component.html'
})
export class EditorDetailComponent implements OnInit {
    editor: IEditor;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ editor }) => {
            this.editor = editor;
        });
    }

    previousState() {
        window.history.back();
    }
}
