/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { HorariosProfesionalDeleteDialogComponent } from 'app/entities/horarios-profesional/horarios-profesional-delete-dialog.component';
import { HorariosProfesionalService } from 'app/entities/horarios-profesional/horarios-profesional.service';

describe('Component Tests', () => {
    describe('HorariosProfesional Management Delete Component', () => {
        let comp: HorariosProfesionalDeleteDialogComponent;
        let fixture: ComponentFixture<HorariosProfesionalDeleteDialogComponent>;
        let service: HorariosProfesionalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [HorariosProfesionalDeleteDialogComponent]
            })
                .overrideTemplate(HorariosProfesionalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorariosProfesionalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorariosProfesionalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
