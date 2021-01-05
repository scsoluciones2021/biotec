/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { PacienteDetailComponent } from 'app/entities/paciente/paciente-detail.component';
import { Paciente } from 'app/shared/model/paciente.model';

describe('Component Tests', () => {
    describe('Paciente Management Detail Component', () => {
        let comp: PacienteDetailComponent;
        let fixture: ComponentFixture<PacienteDetailComponent>;
        const route = ({ data: of({ paciente: new Paciente(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [PacienteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PacienteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PacienteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.paciente).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
