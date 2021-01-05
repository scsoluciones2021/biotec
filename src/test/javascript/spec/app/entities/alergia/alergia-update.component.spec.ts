/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AlergiaUpdateComponent } from 'app/entities/alergia/alergia-update.component';
import { AlergiaService } from 'app/entities/alergia/alergia.service';
import { Alergia } from 'app/shared/model/alergia.model';

describe('Component Tests', () => {
    describe('Alergia Management Update Component', () => {
        let comp: AlergiaUpdateComponent;
        let fixture: ComponentFixture<AlergiaUpdateComponent>;
        let service: AlergiaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AlergiaUpdateComponent]
            })
                .overrideTemplate(AlergiaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AlergiaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlergiaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Alergia(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.alergia = entity;
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
                    const entity = new Alergia();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.alergia = entity;
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
