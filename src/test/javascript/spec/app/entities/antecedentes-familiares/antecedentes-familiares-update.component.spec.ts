/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesFamiliaresUpdateComponent } from 'app/entities/antecedentes-familiares/antecedentes-familiares-update.component';
import { AntecedentesFamiliaresService } from 'app/entities/antecedentes-familiares/antecedentes-familiares.service';
import { AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

describe('Component Tests', () => {
    describe('AntecedentesFamiliares Management Update Component', () => {
        let comp: AntecedentesFamiliaresUpdateComponent;
        let fixture: ComponentFixture<AntecedentesFamiliaresUpdateComponent>;
        let service: AntecedentesFamiliaresService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesFamiliaresUpdateComponent]
            })
                .overrideTemplate(AntecedentesFamiliaresUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AntecedentesFamiliaresUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AntecedentesFamiliaresService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AntecedentesFamiliares(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.antecedentesFamiliares = entity;
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
                    const entity = new AntecedentesFamiliares();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.antecedentesFamiliares = entity;
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
