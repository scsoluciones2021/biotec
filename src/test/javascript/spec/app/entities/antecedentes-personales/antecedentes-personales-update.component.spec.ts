/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesPersonalesUpdateComponent } from 'app/entities/antecedentes-personales/antecedentes-personales-update.component';
import { AntecedentesPersonalesService } from 'app/entities/antecedentes-personales/antecedentes-personales.service';
import { AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

describe('Component Tests', () => {
    describe('AntecedentesPersonales Management Update Component', () => {
        let comp: AntecedentesPersonalesUpdateComponent;
        let fixture: ComponentFixture<AntecedentesPersonalesUpdateComponent>;
        let service: AntecedentesPersonalesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesPersonalesUpdateComponent]
            })
                .overrideTemplate(AntecedentesPersonalesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AntecedentesPersonalesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AntecedentesPersonalesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AntecedentesPersonales(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.antecedentesPersonales = entity;
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
                    const entity = new AntecedentesPersonales();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.antecedentesPersonales = entity;
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
