/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestWebTestModule } from '../../../test.module';
import { ConstantesComponent } from 'app/entities/constantes/constantes.component';
import { ConstantesService } from 'app/entities/constantes/constantes.service';
import { Constantes } from 'app/shared/model/constantes.model';

describe('Component Tests', () => {
    describe('Constantes Management Component', () => {
        let comp: ConstantesComponent;
        let fixture: ComponentFixture<ConstantesComponent>;
        let service: ConstantesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [ConstantesComponent],
                providers: []
            })
                .overrideTemplate(ConstantesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConstantesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConstantesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Constantes(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.constantes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
