/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { BloqueosDeleteDialogComponent } from 'app/entities/bloqueos/bloqueos-delete-dialog.component';
import { BloqueosService } from 'app/entities/bloqueos/bloqueos.service';

describe('Component Tests', () => {
    describe('Bloqueos Management Delete Component', () => {
        let comp: BloqueosDeleteDialogComponent;
        let fixture: ComponentFixture<BloqueosDeleteDialogComponent>;
        let service: BloqueosService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [BloqueosDeleteDialogComponent]
            })
                .overrideTemplate(BloqueosDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BloqueosDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BloqueosService);
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
