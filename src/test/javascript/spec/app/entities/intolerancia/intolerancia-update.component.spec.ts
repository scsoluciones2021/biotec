/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { IntoleranciaUpdateComponent } from 'app/entities/intolerancia/intolerancia-update.component';
import { IntoleranciaService } from 'app/entities/intolerancia/intolerancia.service';
import { Intolerancia } from 'app/shared/model/intolerancia.model';

describe('Component Tests', () => {
    describe('Intolerancia Management Update Component', () => {
        let comp: IntoleranciaUpdateComponent;
        let fixture: ComponentFixture<IntoleranciaUpdateComponent>;
        let service: IntoleranciaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [IntoleranciaUpdateComponent]
            })
                .overrideTemplate(IntoleranciaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IntoleranciaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntoleranciaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Intolerancia(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.intolerancia = entity;
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
                    const entity = new Intolerancia();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.intolerancia = entity;
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
