/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { EnfermedadUpdateComponent } from 'app/entities/enfermedad/enfermedad-update.component';
import { EnfermedadService } from 'app/entities/enfermedad/enfermedad.service';
import { Enfermedad } from 'app/shared/model/enfermedad.model';

describe('Component Tests', () => {
    describe('Enfermedad Management Update Component', () => {
        let comp: EnfermedadUpdateComponent;
        let fixture: ComponentFixture<EnfermedadUpdateComponent>;
        let service: EnfermedadService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EnfermedadUpdateComponent]
            })
                .overrideTemplate(EnfermedadUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnfermedadUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnfermedadService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Enfermedad(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.enfermedad = entity;
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
                    const entity = new Enfermedad();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.enfermedad = entity;
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
