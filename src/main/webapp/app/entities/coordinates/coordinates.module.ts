import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TheLibrarySharedModule } from 'app/shared';
import {
    CoordinatesComponent,
    CoordinatesDetailComponent,
    CoordinatesUpdateComponent,
    CoordinatesDeletePopupComponent,
    CoordinatesDeleteDialogComponent,
    coordinatesRoute,
    coordinatesPopupRoute
} from './';

const ENTITY_STATES = [...coordinatesRoute, ...coordinatesPopupRoute];

@NgModule({
    imports: [TheLibrarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CoordinatesComponent,
        CoordinatesDetailComponent,
        CoordinatesUpdateComponent,
        CoordinatesDeleteDialogComponent,
        CoordinatesDeletePopupComponent
    ],
    entryComponents: [CoordinatesComponent, CoordinatesUpdateComponent, CoordinatesDeleteDialogComponent, CoordinatesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheLibraryCoordinatesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
