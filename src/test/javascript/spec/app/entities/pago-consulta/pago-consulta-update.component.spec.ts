/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { PagoConsultaUpdateComponent } from 'app/entities/pago-consulta/pago-consulta-update.component';
import { PagoConsultaService } from 'app/entities/pago-consulta/pago-consulta.service';
import { PagoConsulta } from 'app/shared/model/pago-consulta.model';

describe('Component Tests', () => {
    describe('PagoConsulta Management Update Component', () => {
        let comp: PagoConsultaUpdateComponent;
        let fixture: ComponentFixture<PagoConsultaUpdateComponent>;
        let service: PagoConsultaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [PagoConsultaUpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(PagoConsultaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PagoConsultaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagoConsultaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PagoConsulta(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    // comp.updateForm(entity);
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
                    const entity = new PagoConsulta();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    // comp.updateForm(entity);
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
