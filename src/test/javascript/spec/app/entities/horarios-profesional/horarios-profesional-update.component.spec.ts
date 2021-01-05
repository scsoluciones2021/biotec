/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { HorariosProfesionalUpdateComponent } from 'app/entities/horarios-profesional/horarios-profesional-update.component';
import { HorariosProfesionalService } from 'app/entities/horarios-profesional/horarios-profesional.service';
import { HorariosProfesional } from 'app/shared/model/horarios-profesional.model';

describe('Component Tests', () => {
    describe('HorariosProfesional Management Update Component', () => {
        let comp: HorariosProfesionalUpdateComponent;
        let fixture: ComponentFixture<HorariosProfesionalUpdateComponent>;
        let service: HorariosProfesionalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [HorariosProfesionalUpdateComponent]
            })
                .overrideTemplate(HorariosProfesionalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HorariosProfesionalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorariosProfesionalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HorariosProfesional(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.horariosProfesional = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HorariosProfesional();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.horariosProfesional = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
