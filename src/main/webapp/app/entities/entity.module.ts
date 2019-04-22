import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'library',
                loadChildren: './library/library.module#TheLibraryLibraryModule'
            },
            {
                path: 'book',
                loadChildren: './book/book.module#TheLibraryBookModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#TheLibraryCategoryModule'
            },
            {
                path: 'author',
                loadChildren: './author/author.module#TheLibraryAuthorModule'
            },
            {
                path: 'coordinates',
                loadChildren: './coordinates/coordinates.module#TheLibraryCoordinatesModule'
            },
            {
                path: 'editor',
                loadChildren: './editor/editor.module#TheLibraryEditorModule'
            },
            {
                path: 'loan',
                loadChildren: './loan/loan.module#TheLibraryLoanModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#TheLibraryRoleModule'
            },
            {
                path: 'users',
                loadChildren: './users/users.module#TheLibraryUsersModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheLibraryEntityModule {}
