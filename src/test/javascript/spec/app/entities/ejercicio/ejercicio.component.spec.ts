/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { EjercicioComponent } from 'app/entities/ejercicio/ejercicio.component';
import { EjercicioService } from 'app/entities/ejercicio/ejercicio.service';
import { Ejercicio } from 'app/shared/model/ejercicio.model';

describe('Component Tests', () => {
    describe('Ejercicio Management Component', () => {
        let comp: EjercicioComponent;
        let fixture: ComponentFixture<EjercicioComponent>;
        let service: EjercicioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EjercicioComponent],
                providers: []
            })
                .overrideTemplate(EjercicioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EjercicioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EjercicioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Ejercicio(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ejercicios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
