/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { PagoConsultaDetailComponent } from 'app/entities/pago-consulta/pago-consulta-detail.component';
import { PagoConsulta } from 'app/shared/model/pago-consulta.model';

describe('Component Tests', () => {
    describe('PagoConsulta Management Detail Component', () => {
        let comp: PagoConsultaDetailComponent;
        let fixture: ComponentFixture<PagoConsultaDetailComponent>;
        const route = ({ data: of({ pagoConsulta: new PagoConsulta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [PagoConsultaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PagoConsultaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PagoConsultaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pagoConsulta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
