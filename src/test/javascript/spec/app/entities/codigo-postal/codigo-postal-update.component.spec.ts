/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { CodigoPostalUpdateComponent } from 'app/entities/codigo-postal/codigo-postal-update.component';
import { CodigoPostalService } from 'app/entities/codigo-postal/codigo-postal.service';
import { CodigoPostal } from 'app/shared/model/codigo-postal.model';

describe('Component Tests', () => {
    describe('CodigoPostal Management Update Component', () => {
        let comp: CodigoPostalUpdateComponent;
        let fixture: ComponentFixture<CodigoPostalUpdateComponent>;
        let service: CodigoPostalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [CodigoPostalUpdateComponent]
            })
                .overrideTemplate(CodigoPostalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CodigoPostalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CodigoPostalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CodigoPostal(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.codigoPostal = entity;
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
                    const entity = new CodigoPostal();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.codigoPostal = entity;
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
