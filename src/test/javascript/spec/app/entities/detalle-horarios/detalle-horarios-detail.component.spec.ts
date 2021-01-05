/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { DetalleHorariosDetailComponent } from 'app/entities/detalle-horarios/detalle-horarios-detail.component';
import { DetalleHorarios } from 'app/shared/model/detalle-horarios.model';

describe('Component Tests', () => {
    describe('DetalleHorarios Management Detail Component', () => {
        let comp: DetalleHorariosDetailComponent;
        let fixture: ComponentFixture<DetalleHorariosDetailComponent>;
        const route = ({ data: of({ detalleHorarios: new DetalleHorarios(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [DetalleHorariosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DetalleHorariosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetalleHorariosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.detalleHorarios).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
