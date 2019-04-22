/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TheLibraryTestModule } from '../../../test.module';
import { EditorUpdateComponent } from 'app/entities/editor/editor-update.component';
import { EditorService } from 'app/entities/editor/editor.service';
import { Editor } from 'app/shared/model/editor.model';

describe('Component Tests', () => {
    describe('Editor Management Update Component', () => {
        let comp: EditorUpdateComponent;
        let fixture: ComponentFixture<EditorUpdateComponent>;
        let service: EditorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [EditorUpdateComponent]
            })
                .overrideTemplate(EditorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EditorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EditorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Editor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.editor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Editor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.editor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
