import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Categoriascie10UpdateComponent } from 'app/entities/categoriascie10/categoriascie10-update.component';
import { Categoriascie10Service } from 'app/entities/categoriascie10/categoriascie10.service';
import { Categoriascie10 } from 'app/shared/model/categoriascie10.model';

describe('Component Tests', () => {
    describe('Categoriascie10 Management Update Component', () => {
        let comp: Categoriascie10UpdateComponent;
        let fixture: ComponentFixture<Categoriascie10UpdateComponent>;
        let service: Categoriascie10Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Categoriascie10UpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(Categoriascie10UpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Categoriascie10UpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Categoriascie10Service);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Categoriascie10(123);
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
                    const entity = new Categoriascie10();
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
