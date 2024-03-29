/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { BebidaDeleteDialogComponent } from 'app/entities/bebida/bebida-delete-dialog.component';
import { BebidaService } from 'app/entities/bebida/bebida.service';

describe('Component Tests', () => {
    describe('Bebida Management Delete Component', () => {
        let comp: BebidaDeleteDialogComponent;
        let fixture: ComponentFixture<BebidaDeleteDialogComponent>;
        let service: BebidaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [BebidaDeleteDialogComponent]
            })
                .overrideTemplate(BebidaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BebidaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BebidaService);
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
