/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { RegimenUpdateComponent } from 'app/entities/regimen/regimen-update.component';
import { RegimenService } from 'app/entities/regimen/regimen.service';
import { Regimen } from 'app/shared/model/regimen.model';

describe('Component Tests', () => {
    describe('Regimen Management Update Component', () => {
        let comp: RegimenUpdateComponent;
        let fixture: ComponentFixture<RegimenUpdateComponent>;
        let service: RegimenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [RegimenUpdateComponent]
            })
                .overrideTemplate(RegimenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegimenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimenService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Regimen(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regimen = entity;
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
                    const entity = new Regimen();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regimen = entity;
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
