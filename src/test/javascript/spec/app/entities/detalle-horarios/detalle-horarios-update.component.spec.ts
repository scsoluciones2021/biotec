/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { DetalleHorariosUpdateComponent } from 'app/entities/detalle-horarios/detalle-horarios-update.component';
import { DetalleHorariosService } from 'app/entities/detalle-horarios/detalle-horarios.service';
import { DetalleHorarios } from 'app/shared/model/detalle-horarios.model';

describe('Component Tests', () => {
    describe('DetalleHorarios Management Update Component', () => {
        let comp: DetalleHorariosUpdateComponent;
        let fixture: ComponentFixture<DetalleHorariosUpdateComponent>;
        let service: DetalleHorariosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [DetalleHorariosUpdateComponent]
            })
                .overrideTemplate(DetalleHorariosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DetalleHorariosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleHorariosService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DetalleHorarios(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detalleHorarios = entity;
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
                    const entity = new DetalleHorarios();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detalleHorarios = entity;
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
