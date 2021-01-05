/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { IntoleranciaComponent } from 'app/entities/intolerancia/intolerancia.component';
import { IntoleranciaService } from 'app/entities/intolerancia/intolerancia.service';
import { Intolerancia } from 'app/shared/model/intolerancia.model';

describe('Component Tests', () => {
    describe('Intolerancia Management Component', () => {
        let comp: IntoleranciaComponent;
        let fixture: ComponentFixture<IntoleranciaComponent>;
        let service: IntoleranciaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [IntoleranciaComponent],
                providers: []
            })
                .overrideTemplate(IntoleranciaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IntoleranciaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntoleranciaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Intolerancia(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.intolerancias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
