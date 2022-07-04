/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestWebTestModule } from '../../../test.module';
import { TituloShortComponent } from 'app/entities/titulo-short/titulo-short.component';
import { TituloShortService } from 'app/entities/titulo-short/titulo-short.service';
import { TituloShort } from 'app/shared/model/titulo-short.model';

describe('Component Tests', () => {
    describe('TituloShort Management Component', () => {
        let comp: TituloShortComponent;
        let fixture: ComponentFixture<TituloShortComponent>;
        let service: TituloShortService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [TituloShortComponent],
                providers: []
            })
                .overrideTemplate(TituloShortComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TituloShortComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TituloShortService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TituloShort(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tituloShorts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
