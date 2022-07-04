/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { HorariosProfesionalDetailComponent } from 'app/entities/horarios-profesional/horarios-profesional-detail.component';
import { HorariosProfesional } from 'app/shared/model/horarios-profesional.model';

describe('Component Tests', () => {
    describe('HorariosProfesional Management Detail Component', () => {
        let comp: HorariosProfesionalDetailComponent;
        let fixture: ComponentFixture<HorariosProfesionalDetailComponent>;
        const route = ({ data: of({ horariosProfesional: new HorariosProfesional(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [HorariosProfesionalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HorariosProfesionalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorariosProfesionalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.horariosProfesional).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
