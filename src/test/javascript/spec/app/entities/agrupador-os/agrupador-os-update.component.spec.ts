/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AgrupadorOSUpdateComponent } from 'app/entities/agrupador-os/agrupador-os-update.component';
import { AgrupadorOSService } from 'app/entities/agrupador-os/agrupador-os.service';
import { AgrupadorOS } from 'app/shared/model/agrupador-os.model';

describe('Component Tests', () => {
    describe('AgrupadorOS Management Update Component', () => {
        let comp: AgrupadorOSUpdateComponent;
        let fixture: ComponentFixture<AgrupadorOSUpdateComponent>;
        let service: AgrupadorOSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AgrupadorOSUpdateComponent]
            })
                .overrideTemplate(AgrupadorOSUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AgrupadorOSUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgrupadorOSService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AgrupadorOS(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.agrupadorOS = entity;
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
                    const entity = new AgrupadorOS();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.agrupadorOS = entity;
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
