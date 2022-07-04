/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { FamiliarUpdateComponent } from 'app/entities/familiar/familiar-update.component';
import { FamiliarService } from 'app/entities/familiar/familiar.service';
import { Familiar } from 'app/shared/model/familiar.model';

describe('Component Tests', () => {
    describe('Familiar Management Update Component', () => {
        let comp: FamiliarUpdateComponent;
        let fixture: ComponentFixture<FamiliarUpdateComponent>;
        let service: FamiliarService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [FamiliarUpdateComponent]
            })
                .overrideTemplate(FamiliarUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FamiliarUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamiliarService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Familiar(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.familiar = entity;
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
                    const entity = new Familiar();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.familiar = entity;
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
