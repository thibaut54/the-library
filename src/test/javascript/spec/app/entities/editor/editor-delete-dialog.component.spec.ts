/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TheLibraryTestModule } from '../../../test.module';
import { EditorDeleteDialogComponent } from 'app/entities/editor/editor-delete-dialog.component';
import { EditorService } from 'app/entities/editor/editor.service';

describe('Component Tests', () => {
    describe('Editor Management Delete Component', () => {
        let comp: EditorDeleteDialogComponent;
        let fixture: ComponentFixture<EditorDeleteDialogComponent>;
        let service: EditorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [EditorDeleteDialogComponent]
            })
                .overrideTemplate(EditorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EditorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EditorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
