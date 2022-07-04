/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { AdjuntosFichaUpdateComponent } from 'app/entities/adjuntos-ficha/adjuntos-ficha-update.component';
import { AdjuntosFichaService } from 'app/entities/adjuntos-ficha/adjuntos-ficha.service';
import { AdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';

describe('Component Tests', () => {
    describe('AdjuntosFicha Management Update Component', () => {
        let comp: AdjuntosFichaUpdateComponent;
        let fixture: ComponentFixture<AdjuntosFichaUpdateComponent>;
        let service: AdjuntosFichaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [AdjuntosFichaUpdateComponent]
            })
                .overrideTemplate(AdjuntosFichaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdjuntosFichaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdjuntosFichaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdjuntosFicha(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adjuntos_ficha = entity;
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
                    const entity = new AdjuntosFicha();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adjuntos_ficha = entity;
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
