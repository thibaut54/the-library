/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TheLibraryTestModule } from '../../../test.module';
import { CoordinatesUpdateComponent } from 'app/entities/coordinates/coordinates-update.component';
import { CoordinatesService } from 'app/entities/coordinates/coordinates.service';
import { Coordinates } from 'app/shared/model/coordinates.model';

describe('Component Tests', () => {
    describe('Coordinates Management Update Component', () => {
        let comp: CoordinatesUpdateComponent;
        let fixture: ComponentFixture<CoordinatesUpdateComponent>;
        let service: CoordinatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [CoordinatesUpdateComponent]
            })
                .overrideTemplate(CoordinatesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CoordinatesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoordinatesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Coordinates(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.coordinates = entity;
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
                    const entity = new Coordinates();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.coordinates = entity;
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
