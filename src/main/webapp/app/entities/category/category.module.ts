import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TheLibrarySharedModule } from 'app/shared';
import {
    CategoryComponent,
    CategoryDetailComponent,
    CategoryUpdateComponent,
    CategoryDeletePopupComponent,
    CategoryDeleteDialogComponent,
    categoryRoute,
    categoryPopupRoute
} from './';

const ENTITY_STATES = [...categoryRoute, ...categoryPopupRoute];

@NgModule({
    imports: [TheLibrarySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoryComponent,
        CategoryDetailComponent,
        CategoryUpdateComponent,
        CategoryDeleteDialogComponent,
        CategoryDeletePopupComponent
    ],
    entryComponents: [CategoryComponent, CategoryUpdateComponent, CategoryDeleteDialogComponent, CategoryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheLibraryCategoryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
