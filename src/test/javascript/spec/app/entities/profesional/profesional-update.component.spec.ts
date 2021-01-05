/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { ProfesionalUpdateComponent } from 'app/entities/profesional/profesional-update.component';
import { ProfesionalService } from 'app/entities/profesional/profesional.service';
import { Profesional } from 'app/shared/model/profesional.model';

describe('Component Tests', () => {
    describe('Profesional Management Update Component', () => {
        let comp: ProfesionalUpdateComponent;
        let fixture: ComponentFixture<ProfesionalUpdateComponent>;
        let service: ProfesionalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ProfesionalUpdateComponent]
            })
                .overrideTemplate(ProfesionalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProfesionalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesionalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Profesional(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.profesional = entity;
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
                    const entity = new Profesional();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.profesional = entity;
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
