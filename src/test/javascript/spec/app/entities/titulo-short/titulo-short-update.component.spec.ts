/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { TituloShortUpdateComponent } from 'app/entities/titulo-short/titulo-short-update.component';
import { TituloShortService } from 'app/entities/titulo-short/titulo-short.service';
import { TituloShort } from 'app/shared/model/titulo-short.model';

describe('Component Tests', () => {
    describe('TituloShort Management Update Component', () => {
        let comp: TituloShortUpdateComponent;
        let fixture: ComponentFixture<TituloShortUpdateComponent>;
        let service: TituloShortService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [TituloShortUpdateComponent]
            })
                .overrideTemplate(TituloShortUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TituloShortUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TituloShortService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TituloShort(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tituloShort = entity;
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
                    const entity = new TituloShort();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tituloShort = entity;
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
