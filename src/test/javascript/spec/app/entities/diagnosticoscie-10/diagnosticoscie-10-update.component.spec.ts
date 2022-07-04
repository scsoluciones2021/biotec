import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Diagnosticoscie10UpdateComponent } from 'app/entities/diagnosticoscie10/diagnosticoscie10-update.component';
import { Diagnosticoscie10Service } from 'app/entities/diagnosticoscie10/diagnosticoscie10.service';
import { Diagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';

describe('Component Tests', () => {
    describe('Diagnosticoscie10 Management Update Component', () => {
        let comp: Diagnosticoscie10UpdateComponent;
        let fixture: ComponentFixture<Diagnosticoscie10UpdateComponent>;
        let service: Diagnosticoscie10Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Diagnosticoscie10UpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(Diagnosticoscie10UpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Diagnosticoscie10UpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Diagnosticoscie10Service);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Diagnosticoscie10(123);
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
                    const entity = new Diagnosticoscie10();
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
