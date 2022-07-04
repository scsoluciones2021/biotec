import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { GestWebTestModule } from '../../../test.module';
import { Categoriascie10Component } from 'app/entities/categoriascie10/categoriascie10.component';
import { Categoriascie10Service } from 'app/entities/categoriascie10/categoriascie10.service';
import { Categoriascie10 } from 'app/shared/model/categoriascie10.model';

describe('Component Tests', () => {
    describe('Categoriascie10 Management Component', () => {
        let comp: Categoriascie10Component;
        let fixture: ComponentFixture<Categoriascie10Component>;
        let service: Categoriascie10Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Categoriascie10Component],
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
                .overrideTemplate(Categoriascie10Component, '')
                .compileComponents();

            fixture = TestBed.createComponent(Categoriascie10Component);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Categoriascie10Service);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Categoriascie10(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.categoriascie10s && comp.categoriascie10s[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Categoriascie10(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.categoriascie10s && comp.categoriascie10s[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
