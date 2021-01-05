/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { BebidaUpdateComponent } from 'app/entities/bebida/bebida-update.component';
import { BebidaService } from 'app/entities/bebida/bebida.service';
import { Bebida } from 'app/shared/model/bebida.model';

describe('Component Tests', () => {
    describe('Bebida Management Update Component', () => {
        let comp: BebidaUpdateComponent;
        let fixture: ComponentFixture<BebidaUpdateComponent>;
        let service: BebidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [BebidaUpdateComponent]
            })
                .overrideTemplate(BebidaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BebidaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BebidaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bebida(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bebida = entity;
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
                    const entity = new Bebida();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bebida = entity;
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
