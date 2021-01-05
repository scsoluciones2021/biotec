/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { ObsAntecPersonalUpdateComponent } from 'app/entities/obs-antec-personal/obs-antec-personal-update.component';
import { ObsAntecPersonalService } from 'app/entities/obs-antec-personal/obs-antec-personal.service';
import { ObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';

describe('Component Tests', () => {
    describe('ObsAntecPersonal Management Update Component', () => {
        let comp: ObsAntecPersonalUpdateComponent;
        let fixture: ComponentFixture<ObsAntecPersonalUpdateComponent>;
        let service: ObsAntecPersonalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ObsAntecPersonalUpdateComponent]
            })
                .overrideTemplate(ObsAntecPersonalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ObsAntecPersonalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObsAntecPersonalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ObsAntecPersonal(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.obsAntecPersonal = entity;
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
                    const entity = new ObsAntecPersonal();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.obsAntecPersonal = entity;
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
