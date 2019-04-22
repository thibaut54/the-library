/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TheLibraryTestModule } from '../../../test.module';
import { LoanComponent } from 'app/entities/loan/loan.component';
import { LoanService } from 'app/entities/loan/loan.service';
import { Loan } from 'app/shared/model/loan.model';

describe('Component Tests', () => {
    describe('Loan Management Component', () => {
        let comp: LoanComponent;
        let fixture: ComponentFixture<LoanComponent>;
        let service: LoanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TheLibraryTestModule],
                declarations: [LoanComponent],
                providers: []
            })
                .overrideTemplate(LoanComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoanService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Loan(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.loans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
