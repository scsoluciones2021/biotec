/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { ProfesionalDetailComponent } from 'app/entities/profesional/profesional-detail.component';
import { Profesional } from 'app/shared/model/profesional.model';

describe('Component Tests', () => {
    describe('Profesional Management Detail Component', () => {
        let comp: ProfesionalDetailComponent;
        let fixture: ComponentFixture<ProfesionalDetailComponent>;
        const route = ({ data: of({ profesional: new Profesional(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [ProfesionalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProfesionalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProfesionalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.profesional).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
