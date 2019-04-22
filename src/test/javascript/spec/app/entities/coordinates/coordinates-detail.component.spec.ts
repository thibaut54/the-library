/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TheLibraryTestModule } from '../../../test.module';
import { CoordinatesDetailComponent } from 'app/entities/coordinates/coordinates-detail.component';
import { Coordinates } from 'app/shared/model/coordinates.model';

describe('Component Tests', () => {
    describe('Coordinates Management Detail Component', () => {
        let comp: CoordinatesDetailComponent;
        let fixture: ComponentFixture<CoordinatesDetailComponent>;
        const route = ({ data: of({ coordinates: new Coordinates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [CoordinatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CoordinatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CoordinatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.coordinates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
