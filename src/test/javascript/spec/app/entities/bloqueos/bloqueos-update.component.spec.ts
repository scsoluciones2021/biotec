/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { BloqueosUpdateComponent } from 'app/entities/bloqueos/bloqueos-update.component';
import { BloqueosService } from 'app/entities/bloqueos/bloqueos.service';
import { Bloqueos } from 'app/shared/model/bloqueos.model';

describe('Component Tests', () => {
    describe('Bloqueos Management Update Component', () => {
        let comp: BloqueosUpdateComponent;
        let fixture: ComponentFixture<BloqueosUpdateComponent>;
        let service: BloqueosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [BloqueosUpdateComponent]
            })
                .overrideTemplate(BloqueosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BloqueosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BloqueosService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bloqueos(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bloqueos = entity;
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
                    const entity = new Bloqueos();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bloqueos = entity;
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
