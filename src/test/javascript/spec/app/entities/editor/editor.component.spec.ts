/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TheLibraryTestModule } from '../../../test.module';
import { EditorComponent } from 'app/entities/editor/editor.component';
import { EditorService } from 'app/entities/editor/editor.service';
import { Editor } from 'app/shared/model/editor.model';

describe('Component Tests', () => {
    describe('Editor Management Component', () => {
        let comp: EditorComponent;
        let fixture: ComponentFixture<EditorComponent>;
        let service: EditorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [EditorComponent],
                providers: []
            })
                .overrideTemplate(EditorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EditorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EditorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Editor(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.editors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
