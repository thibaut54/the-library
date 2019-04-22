/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TheLibraryTestModule } from '../../../test.module';
import { EditorDetailComponent } from 'app/entities/editor/editor-detail.component';
import { Editor } from 'app/shared/model/editor.model';

describe('Component Tests', () => {
    describe('Editor Management Detail Component', () => {
        let comp: EditorDetailComponent;
        let fixture: ComponentFixture<EditorDetailComponent>;
        const route = ({ data: of({ editor: new Editor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [EditorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EditorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EditorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.editor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
