import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TheLibrarySharedModule } from 'app/shared';
import {
    EditorComponent,
    EditorDetailComponent,
    EditorUpdateComponent,
    EditorDeletePopupComponent,
    EditorDeleteDialogComponent,
    editorRoute,
    editorPopupRoute
} from './';

const ENTITY_STATES = [...editorRoute, ...editorPopupRoute];

@NgModule({
    imports: [TheLibrarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EditorComponent, EditorDetailComponent, EditorUpdateComponent, EditorDeleteDialogComponent, EditorDeletePopupComponent],
    entryComponents: [EditorComponent, EditorUpdateComponent, EditorDeleteDialogComponent, EditorDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheLibraryEditorModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
