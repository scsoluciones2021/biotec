import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { Gruposcie10DeleteDialogComponent } from 'app/entities/gruposcie10/gruposcie10-delete-dialog.component';
import { Gruposcie10Service } from 'app/entities/gruposcie10/gruposcie10.service';

describe('Component Tests', () => {
    describe('Gruposcie10 Management Delete Component', () => {
        let comp: Gruposcie10DeleteDialogComponent;
        let fixture: ComponentFixture<Gruposcie10DeleteDialogComponent>;
        let service: Gruposcie10Service;
        let mockEventManager: MockEventManager;
        let mockActiveModal: MockActiveModal;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Gruposcie10DeleteDialogComponent]
            })
                .overrideTemplate(Gruposcie10DeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Gruposcie10DeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Gruposcie10Service);
            mockEventManager = TestBed.get(JhiEventManager);
            mockActiveModal = TestBed.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        //  expect(mockActiveModal.closeSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should not call delete service on clear', () => {
                // GIVEN
                spyOn(service, 'delete');

                // WHEN
                comp.cancel();

                // THEN
                expect(service.delete).not.toHaveBeenCalled();
                expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
            });
        });
    });
});
