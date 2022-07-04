/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { ObsAntecFamiliarUpdateComponent } from 'app/entities/obs-antec-familiar/obs-antec-familiar-update.component';
import { ObsAntecFamiliarService } from 'app/entities/obs-antec-familiar/obs-antec-familiar.service';
import { ObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';

describe('Component Tests', () => {
    describe('ObsAntecFamiliar Management Update Component', () => {
        let comp: ObsAntecFamiliarUpdateComponent;
        let fixture: ComponentFixture<ObsAntecFamiliarUpdateComponent>;
        let service: ObsAntecFamiliarService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [ObsAntecFamiliarUpdateComponent]
            })
                .overrideTemplate(ObsAntecFamiliarUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ObsAntecFamiliarUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObsAntecFamiliarService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ObsAntecFamiliar(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.obsAntecFamiliar = entity;
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
                    const entity = new ObsAntecFamiliar();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.obsAntecFamiliar = entity;
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
