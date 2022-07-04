import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { GestWebTestModule } from '../../../test.module';
import { Subgruposcie10Component } from 'app/entities/subgruposcie10/subgruposcie10.component';
import { Subgruposcie10Service } from 'app/entities/subgruposcie10/subgruposcie10.service';
import { Subgruposcie10 } from 'app/shared/model/subgruposcie10.model';

describe('Component Tests', () => {
    describe('Subgruposcie10 Management Component', () => {
        let comp: Subgruposcie10Component;
        let fixture: ComponentFixture<Subgruposcie10Component>;
        let service: Subgruposcie10Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Subgruposcie10Component],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            data: of({
                                defaultSort: 'id,asc'
                            }),
                            queryParamMap: of(
                                convertToParamMap({
                                    page: '1',
                                    size: '1',
                                    sort: 'id,desc'
                                })
                            )
                        }
                    }
                ]
            })
                .overrideTemplate(Subgruposcie10Component, '')
                .compileComponents();

            fixture = TestBed.createComponent(Subgruposcie10Component);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Subgruposcie10Service);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Subgruposcie10(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.subgruposcie10s && comp.subgruposcie10s[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Subgruposcie10(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.subgruposcie10s && comp.subgruposcie10s[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should calculate the sort attribute for an id', () => {
            // WHEN
            comp.ngOnInit();
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['id,desc']);
        });

        it('should calculate the sort attribute for a non-id attribute', () => {
            // INIT
            comp.ngOnInit();

            // GIVEN
            comp.predicate = 'name';

            // WHEN
            const result = comp.sort();

            // THEN
            expect(result).toEqual(['name,desc', 'id']);
        });
    });
});
