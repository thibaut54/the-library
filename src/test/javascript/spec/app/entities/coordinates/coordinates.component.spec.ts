/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TheLibraryTestModule } from '../../../test.module';
import { CoordinatesComponent } from 'app/entities/coordinates/coordinates.component';
import { CoordinatesService } from 'app/entities/coordinates/coordinates.service';
import { Coordinates } from 'app/shared/model/coordinates.model';

describe('Component Tests', () => {
    describe('Coordinates Management Component', () => {
        let comp: CoordinatesComponent;
        let fixture: ComponentFixture<CoordinatesComponent>;
        let service: CoordinatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [CoordinatesComponent],
                providers: []
            })
                .overrideTemplate(CoordinatesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CoordinatesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoordinatesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Coordinates(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.coordinates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
