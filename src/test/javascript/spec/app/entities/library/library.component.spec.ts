/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TheLibraryTestModule } from '../../../test.module';
import { LibraryComponent } from 'app/entities/library/library.component';
import { LibraryService } from 'app/entities/library/library.service';
import { Library } from 'app/shared/model/library.model';

describe('Component Tests', () => {
    describe('Library Management Component', () => {
        let comp: LibraryComponent;
        let fixture: ComponentFixture<LibraryComponent>;
        let service: LibraryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [LibraryComponent],
                providers: []
            })
                .overrideTemplate(LibraryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LibraryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Library(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.libraries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
