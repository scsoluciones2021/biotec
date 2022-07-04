import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Subgruposcie10UpdateComponent } from 'app/entities/subgruposcie10/subgruposcie10-update.component';
import { Subgruposcie10Service } from 'app/entities/subgruposcie10/subgruposcie10.service';
import { Subgruposcie10 } from 'app/shared/model/subgruposcie10.model';

describe('Component Tests', () => {
    describe('Subgruposcie10 Management Update Component', () => {
        let comp: Subgruposcie10UpdateComponent;
        let fixture: ComponentFixture<Subgruposcie10UpdateComponent>;
        let service: Subgruposcie10Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Subgruposcie10UpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(Subgruposcie10UpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Subgruposcie10UpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Subgruposcie10Service);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Subgruposcie10(123);
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
                    const entity = new Subgruposcie10();
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
