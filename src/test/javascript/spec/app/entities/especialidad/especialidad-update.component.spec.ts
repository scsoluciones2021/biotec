/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { EspecialidadUpdateComponent } from 'app/entities/especialidad/especialidad-update.component';
import { EspecialidadService } from 'app/entities/especialidad/especialidad.service';
import { Especialidad } from 'app/shared/model/especialidad.model';

describe('Component Tests', () => {
    describe('Especialidad Management Update Component', () => {
        let comp: EspecialidadUpdateComponent;
        let fixture: ComponentFixture<EspecialidadUpdateComponent>;
        let service: EspecialidadService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EspecialidadUpdateComponent]
            })
                .overrideTemplate(EspecialidadUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EspecialidadUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EspecialidadService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Especialidad(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.especialidad = entity;
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
                    const entity = new Especialidad();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.especialidad = entity;
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
