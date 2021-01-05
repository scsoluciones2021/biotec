/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { CalendarioComponent } from 'app/entities/calendario/calendario.component';
import { CalendarioService } from 'app/entities/calendario/calendario.service';
import { Calendario } from 'app/shared/model/calendario.model';

describe('Component Tests', () => {
    describe('Calendario Management Component', () => {
        let comp: CalendarioComponent;
        let fixture: ComponentFixture<CalendarioComponent>;
        let service: CalendarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [CalendarioComponent],
                providers: []
            })
                .overrideTemplate(CalendarioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalendarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalendarioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Calendario(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.calendarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
