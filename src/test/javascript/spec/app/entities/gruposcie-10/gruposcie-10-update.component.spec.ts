import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Gruposcie10UpdateComponent } from 'app/entities/gruposcie10/gruposcie10-update.component';
import { Gruposcie10Service } from 'app/entities/gruposcie10/gruposcie10.service';
import { Gruposcie10 } from 'app/shared/model/gruposcie10.model';

describe('Component Tests', () => {
    describe('Gruposcie10 Management Update Component', () => {
        let comp: Gruposcie10UpdateComponent;
        let fixture: ComponentFixture<Gruposcie10UpdateComponent>;
        let service: Gruposcie10Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Gruposcie10UpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(Gruposcie10UpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Gruposcie10UpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Gruposcie10Service);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Gruposcie10(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.updateForm(entity);
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
                    const entity = new Gruposcie10();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.updateForm(entity);
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
