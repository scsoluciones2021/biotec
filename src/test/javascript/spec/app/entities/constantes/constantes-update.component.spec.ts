/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { ConstantesUpdateComponent } from 'app/entities/constantes/constantes-update.component';
import { ConstantesService } from 'app/entities/constantes/constantes.service';
import { Constantes } from 'app/shared/model/constantes.model';

describe('Component Tests', () => {
    describe('Constantes Management Update Component', () => {
        let comp: ConstantesUpdateComponent;
        let fixture: ComponentFixture<ConstantesUpdateComponent>;
        let service: ConstantesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ConstantesUpdateComponent]
            })
                .overrideTemplate(ConstantesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConstantesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConstantesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Constantes(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.constantes = entity;
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
                    const entity = new Constantes();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.constantes = entity;
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
